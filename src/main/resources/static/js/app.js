/**
 * VAYAL – SMART FERTILIZER ADVISOR: APPLICATION CONTROLLER
 * Full client-side interaction, state management, and DOM rendering.
 */

document.addEventListener('DOMContentLoaded', () => {
  App.init();
});

const App = {
  state: {
    crops: [],
    fertilizers: [],
    farmers: [],
    currentRecommendation: null,
    historyList: [],
    hasSoilTest: false
  },

  init() {
    this.bindEvents();
    this.loadInitialData();
    this.checkApiHealth();
  },

  async checkApiHealth() {
    try {
      await Api.getCrops();
      const dot = document.querySelector('.status-dot');
      const text = document.querySelector('.api-status span');
      if (dot && text) {
        dot.style.backgroundColor = '#10b981';
        text.textContent = 'API Connected';
      }
    } catch (e) {
      const dot = document.querySelector('.status-dot');
      const text = document.querySelector('.api-status span');
      if (dot && text) {
        dot.style.backgroundColor = '#ef4444';
        text.textContent = 'Offline / Connecting';
      }
    }
  },

  async loadInitialData() {
    try {
      const [cropsRes, fertRes, farmersRes] = await Promise.all([
        Api.getCrops(),
        Api.getFertilizers(),
        Api.getFarmers()
      ]);

      this.state.crops = cropsRes.data || [];
      this.state.fertilizers = fertRes.data || [];
      this.state.farmers = farmersRes.data || [];

      this.populateCropDropdown();
      this.populateFarmerDropdown();
      this.renderKnowledgeBase();
      this.renderFarmersDirectory();
    } catch (err) {
      this.showToast('Failed to load initial agronomic data. Check server connection.', 'error');
    }
  },

  bindEvents() {
    // Navigation item clicks
    document.querySelectorAll('.nav-item').forEach(item => {
      item.addEventListener('click', (e) => {
        const viewId = e.currentTarget.getAttribute('data-view');
        this.switchView(viewId);
      });
    });

    // Soil test toggle buttons
    const toggleYes = document.getElementById('toggle-soil-yes');
    const toggleNo = document.getElementById('toggle-soil-no');

    if (toggleYes && toggleNo) {
      toggleYes.addEventListener('click', () => this.setSoilTestAvailable(true));
      toggleNo.addEventListener('click', () => this.setSoilTestAvailable(false));
    }

    // Farmer select change (autofill)
    const farmerSelect = document.getElementById('existingFarmerSelect');
    if (farmerSelect) {
      farmerSelect.addEventListener('change', (e) => this.onFarmerSelected(e.target.value));
    }

    // Crop select change (show preferred soil hint)
    const cropSelect = document.getElementById('cropSelect');
    if (cropSelect) {
      cropSelect.addEventListener('change', (e) => this.onCropChanged(e.target.value));
    }

    // Form submission
    const form = document.getElementById('fertilizerAdvisorForm');
    if (form) {
      form.addEventListener('submit', (e) => this.handleFormSubmit(e));
    }

    // Print button
    const printBtn = document.getElementById('btnPrintReport');
    if (printBtn) {
      printBtn.addEventListener('click', () => window.print());
    }

    // History search input
    const historySearch = document.getElementById('historySearchInput');
    if (historySearch) {
      historySearch.addEventListener('input', (e) => this.filterHistory(e.target.value));
    }
  },

  switchView(viewId) {
    document.querySelectorAll('.nav-item').forEach(nav => {
      nav.classList.toggle('active', nav.getAttribute('data-view') === viewId);
    });

    document.querySelectorAll('.view-section').forEach(sec => {
      sec.classList.remove('active');
    });

    const targetSec = document.getElementById(`view-${viewId}`);
    if (targetSec) {
      targetSec.classList.add('active');
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    if (viewId === 'history') {
      this.loadHistory();
    }
  },

  setSoilTestAvailable(isAvailable) {
    this.state.hasSoilTest = isAvailable;

    const toggleYes = document.getElementById('toggle-soil-yes');
    const toggleNo = document.getElementById('toggle-soil-no');
    const soilPanel = document.getElementById('soilTestInputPanel');
    const noReportAlert = document.getElementById('noReportNotice');

    if (toggleYes && toggleNo) {
      toggleYes.classList.toggle('selected', isAvailable);
      toggleNo.classList.toggle('selected', !isAvailable);

      toggleYes.querySelector('input[type="radio"]').checked = isAvailable;
      toggleNo.querySelector('input[type="radio"]').checked = !isAvailable;
    }

    if (soilPanel && noReportAlert) {
      if (isAvailable) {
        soilPanel.style.display = 'block';
        noReportAlert.style.display = 'none';

        // Enable required validation on N, P, K, pH
        document.getElementById('inputN').required = true;
        document.getElementById('inputP').required = true;
        document.getElementById('inputK').required = true;
        document.getElementById('inputPh').required = true;
      } else {
        soilPanel.style.display = 'none';
        noReportAlert.style.display = 'flex';

        // Disable required attributes
        document.getElementById('inputN').required = false;
        document.getElementById('inputP').required = false;
        document.getElementById('inputK').required = false;
        document.getElementById('inputPh').required = false;

        // Clear values so uncalibrated strategy runs clean
        document.getElementById('inputN').value = '';
        document.getElementById('inputP').value = '';
        document.getElementById('inputK').value = '';
        document.getElementById('inputPh').value = '';
      }
    }
  },

  populateCropDropdown() {
    const select = document.getElementById('cropSelect');
    if (!select) return;

    select.innerHTML = '<option value="">-- Choose Crop --</option>';
    this.state.crops.forEach(c => {
      const opt = document.createElement('option');
      opt.value = c.cropCode;
      opt.textContent = `${c.cropName} (${c.category}) - RDF ${c.baseNitrogenKgHa}:${c.basePhosphorusKgHa}:${c.basePotassiumKgHa} kg/ha`;
      select.appendChild(opt);
    });
  },

  populateFarmerDropdown() {
    const select = document.getElementById('existingFarmerSelect');
    if (!select) return;

    select.innerHTML = '<option value="">-- Or enter new farmer details below --</option>';
    this.state.farmers.forEach(f => {
      const opt = document.createElement('option');
      opt.value = f.id;
      opt.textContent = `${f.name} - ${f.location} (${f.farmSizeAcre} Acres)`;
      select.appendChild(opt);
    });
  },

  onFarmerSelected(farmerId) {
    if (!farmerId) return;
    const farmer = this.state.farmers.find(f => f.id == farmerId);
    if (!farmer) return;

    document.getElementById('farmerName').value = farmer.name;
    document.getElementById('farmerPhone').value = farmer.phoneNumber || '';
    document.getElementById('farmerLocation').value = farmer.location;
    document.getElementById('farmSizeAcre').value = farmer.farmSizeAcre;
  },

  onCropChanged(cropCode) {
    const hintElem = document.getElementById('cropSoilHint');
    if (!hintElem) return;

    if (!cropCode) {
      hintElem.textContent = '';
      return;
    }

    const crop = this.state.crops.find(c => c.cropCode === cropCode);
    if (crop) {
      hintElem.innerHTML = `🌿 <strong>Preferred Soil:</strong> ${crop.preferredSoilTypes} | <strong>Optimal pH:</strong> ${crop.optimumPhMin} - ${crop.optimumPhMax}`;
    }
  },

  async handleFormSubmit(e) {
    e.preventDefault();

    // Client-side validation
    const farmerName = document.getElementById('farmerName').value.trim();
    const location = document.getElementById('farmerLocation').value.trim();
    const farmSize = parseFloat(document.getElementById('farmSizeAcre').value);
    const cropCode = document.getElementById('cropSelect').value;
    const soilType = document.getElementById('soilTypeSelect').value;
    const growthStage = document.getElementById('growthStageSelect').value;
    const hasSoilTest = this.state.hasSoilTest;

    if (!farmerName || !location || isNaN(farmSize) || farmSize <= 0 || !cropCode || !soilType || !growthStage) {
      this.showToast('Please fill in all mandatory farmer and crop fields correctly.', 'error');
      return;
    }

    const payload = {
      farmerName,
      phoneNumber: document.getElementById('farmerPhone').value.trim(),
      location,
      farmSizeAcre: farmSize,
      cropCode,
      soilType,
      growthStage,
      hasSoilTestReport: hasSoilTest
    };

    if (hasSoilTest) {
      const nVal = parseFloat(document.getElementById('inputN').value);
      const pVal = parseFloat(document.getElementById('inputP').value);
      const kVal = parseFloat(document.getElementById('inputK').value);
      const phVal = parseFloat(document.getElementById('inputPh').value);
      const unit = document.getElementById('unitSelect').value;

      if (isNaN(nVal) || isNaN(pVal) || isNaN(kVal) || isNaN(phVal)) {
        this.showToast('Please provide valid numerical figures for Nitrogen, Phosphorus, Potassium, and Soil pH.', 'error');
        return;
      }

      if (phVal < 3.0 || phVal > 11.0) {
        this.showToast('Soil pH must be between 3.0 and 11.0.', 'error');
        return;
      }

      payload.nitrogenValue = nVal;
      payload.phosphorusValue = pVal;
      payload.potassiumValue = kVal;
      payload.soilPh = phVal;
      payload.unit = unit;
    }

    const submitBtn = document.getElementById('btnSubmitForm');
    submitBtn.disabled = true;
    submitBtn.innerHTML = 'Analyzing Agronomic Rules... 🌾';

    try {
      const response = await Api.generateRecommendation(payload);
      this.state.currentRecommendation = response.data;
      this.showToast('Fertilizer Recommendation Plan Generated!', 'success');
      this.renderRecommendationResult(response.data);
      this.switchView('result');

      // Refresh farmer list in case a new farmer was created
      const fRes = await Api.getFarmers();
      this.state.farmers = fRes.data || [];
      this.populateFarmerDropdown();
    } catch (err) {
      this.showToast(err.message || 'Error calculating recommendations', 'error');
    } finally {
      submitBtn.disabled = false;
      submitBtn.innerHTML = 'Generate Fertilizer Plan 🌾';
    }
  },

  renderRecommendationResult(rec) {
    if (!rec) return;

    // Helper functions for safe DOM updates
    const setText = (id, val) => {
      const el = document.getElementById(id);
      if (el) el.textContent = val != null ? val : '';
    };

    const setHtml = (id, html) => {
      const el = document.getElementById(id);
      if (el) el.innerHTML = html != null ? html : '';
    };

    // Header info
    setText('resFarmerName', rec.farmerName);
    setText('resFarmSize', `${rec.farmSizeAcre} Acres`);
    setText('resCropName', `${rec.cropName} (${rec.scientificName || ''})`);
    setText('resGrowthStage', rec.growthStageName || rec.growthStage);
    setText('resSoilType', rec.soilType);
    if (rec.createdAt) {
      setText('resDate', new Date(rec.createdAt).toLocaleDateString('en-IN', {
        year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit'
      }));
    }

    const badge = document.getElementById('resStatusBadge');
    if (badge) {
      if (rec.hasSoilTestReport) {
        badge.className = 'result-badge badge-calibrated';
        badge.textContent = '✓ Soil-Test Calibrated Plan (ICAR STCR)';
      } else {
        badge.className = 'result-badge badge-uncalibrated';
        badge.textContent = '⚠️ General Guidance (Soil Test Pending)';
      }
    }

    // Soil Health Section
    const soilTestedSec = document.getElementById('resSoilTestedSection');
    const noReportSec = document.getElementById('resNoReportSection');

    if (rec.hasSoilTestReport && rec.soilHealthSummary) {
      if (soilTestedSec) soilTestedSec.style.display = 'block';
      if (noReportSec) noReportSec.style.display = 'none';

      const sh = rec.soilHealthSummary;
      const nRating = (sh.nitrogenRating || 'MEDIUM').toLowerCase();
      const pRating = (sh.phosphorusRating || 'MEDIUM').toLowerCase();
      const kRating = (sh.potassiumRating || 'MEDIUM').toLowerCase();

      setText('resNVal', `${sh.nitrogenNormalisedKgHa || 0} kg/ha`);
      setText('resNRating', sh.nitrogenRating || 'N/A');
      const elN = document.getElementById('resNRating');
      if (elN) elN.className = `nutrient-rating-badge rating-${nRating}`;

      setText('resPVal', `${sh.phosphorusNormalisedKgHa || 0} kg/ha`);
      setText('resPRating', sh.phosphorusRating || 'N/A');
      const elP = document.getElementById('resPRating');
      if (elP) elP.className = `nutrient-rating-badge rating-${pRating}`;

      setText('resKVal', `${sh.potassiumNormalisedKgHa || 0} kg/ha`);
      setText('resKRating', sh.potassiumRating || 'N/A');
      const elK = document.getElementById('resKRating');
      if (elK) elK.className = `nutrient-rating-badge rating-${kRating}`;

      setText('resPhVal', `pH ${sh.phValue != null ? sh.phValue : '-'}`);
      setText('resPhClass', sh.phClassification || '-');
      setText('resPhAdvisory', rec.phAdvisory || 'pH is within normal range.');
    } else {
      if (soilTestedSec) soilTestedSec.style.display = 'none';
      if (noReportSec) noReportSec.style.display = 'block';
      setHtml('resSoilTestingGuide', (rec.soilTestingGuidance || '').replace(/\n/g, '<br/>'));
    }

    // Current stage nutrient requirement
    const currStageReq = rec.currentStageNutrientRequirement;
    if (currStageReq && rec.hasSoilTestReport) {
      setHtml('currStageNutrients', `
        <strong>Current Stage Demand (for ${rec.farmSizeAcre} acres):</strong>
        <span style="color: var(--primary);">N: ${currStageReq.nitrogenKg || 0} kg</span> |
        <span style="color: #0284c7;">P₂O₅: ${currStageReq.phosphorusKg || 0} kg</span> |
        <span style="color: #d97706;">K₂O: ${currStageReq.potassiumKg || 0} kg</span>
      `);
    } else {
      setHtml('currStageNutrients', `
        <em>Chemical fertilizer dosages are deliberately omitted pending laboratory soil testing.</em>
      `);
    }

    // Commercial fertilizer bags
    const bagContainer = document.getElementById('resCommercialBags');
    if (bagContainer) {
      bagContainer.innerHTML = '';
      if (rec.currentStageFertilizerBags && rec.currentStageFertilizerBags.length > 0) {
        rec.currentStageFertilizerBags.forEach(b => {
          const bagCard = document.createElement('div');
          bagCard.className = 'bag-card';
          bagCard.innerHTML = `
            <div class="bag-header">
              <div>
                <div class="bag-title">${b.fertilizerName}</div>
                <div class="bag-unit">${b.shortName} • ${b.bagWeightKg} kg Bag</div>
              </div>
              <div class="bag-count">${b.bagsCount} <span style="font-size: 0.9rem; font-weight: normal;">Bags</span></div>
            </div>
            <p style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 8px;">
              Total Quantity: <strong>${b.quantityKg} kg</strong> (for ${rec.farmSizeAcre} acres)
            </p>
            <div class="bag-method">
              <strong>Application Note:</strong> ${b.applicationMethod}
            </div>
          `;
          bagContainer.appendChild(bagCard);
        });
      } else {
        bagContainer.innerHTML = `
          <div style="grid-column: 1 / -1; padding: 20px; background: #f8fafc; border-radius: var(--radius-md); border: 1px solid var(--border-color); color: var(--text-muted); font-size: 0.9rem;">
            ${rec.hasSoilTestReport ? 'No commercial fertilizer required for this particular stage.' : 'Commercial chemical fertilizer dosages are omitted pending soil test calibration to protect your soil health.'}
          </div>
        `;
      }
    }

    // Split schedule table
    const tableBody = document.getElementById('resScheduleTableBody');
    if (tableBody) {
      tableBody.innerHTML = '';
      if (rec.stageSplitSchedule && rec.stageSplitSchedule.length > 0) {
        rec.stageSplitSchedule.forEach(s => {
          const row = document.createElement('tr');
          const isCurrent = s.stageCode && rec.growthStage && s.stageCode.toUpperCase() === rec.growthStage.toUpperCase();
          if (isCurrent) {
            row.style.backgroundColor = 'var(--primary-subtle)';
            row.style.fontWeight = 'bold';
          }
          row.innerHTML = `
            <td>${s.stageName} ${isCurrent ? '<span style="color: var(--primary); font-size: 0.75rem; border: 1px solid var(--primary); padding: 2px 6px; border-radius: 4px;">CURRENT</span>' : ''}</td>
            <td>${s.nPercent}% ${s.nKg !== null && s.nKg !== undefined ? `(${s.nKg} kg)` : ''}</td>
            <td>${s.pPercent}% ${s.pKg !== null && s.pKg !== undefined ? `(${s.pKg} kg)` : ''}</td>
            <td>${s.kPercent}% ${s.kKg !== null && s.kKg !== undefined ? `(${s.kKg} kg)` : ''}</td>
            <td><small>${s.instructions || '-'}</small></td>
          `;
          tableBody.appendChild(row);
        });
      }
    }

    // Organic and Biofertilizer Plan
    if (rec.organicAndBioPlan) {
      setText('resFymTons', `${rec.organicAndBioPlan.fymTons != null ? rec.organicAndBioPlan.fymTons : 0} Tons FYM`);
      setText('resVermicompost', rec.organicAndBioPlan.vermicompostRecommendation);
      setText('resBiofertilizers', rec.organicAndBioPlan.biofertilizers);
      setText('resGreenManure', rec.organicAndBioPlan.greenManureRecommendation);
    }

    // Best Management Practices & Warnings
    const bmpList = document.getElementById('resBmpList');
    if (bmpList) {
      bmpList.innerHTML = '';
      (rec.bestManagementPractices || []).forEach(b => {
        const li = document.createElement('li');
        li.textContent = b;
        bmpList.appendChild(li);
      });
    }

    const warnList = document.getElementById('resWarnList');
    if (warnList) {
      warnList.innerHTML = '';
      (rec.agronomicWarnings || []).forEach(w => {
        const li = document.createElement('li');
        li.textContent = w;
        warnList.appendChild(li);
      });
    }
  },

  async loadHistory() {
    const listBody = document.getElementById('historyTableBody');
    if (!listBody) return;

    listBody.innerHTML = '<tr><td colspan="7" style="text-align: center; padding: 25px;">Loading recommendations history...</td></tr>';

    try {
      const res = await Api.getRecommendations();
      this.state.historyList = res.data || [];
      this.renderHistoryTable(this.state.historyList);
    } catch (err) {
      listBody.innerHTML = '<tr><td colspan="7" style="text-align: center; color: var(--status-low); padding: 25px;">Failed to load history.</td></tr>';
    }
  },

  renderHistoryTable(items) {
    const listBody = document.getElementById('historyTableBody');
    if (!listBody) return;

    if (!items || items.length === 0) {
      listBody.innerHTML = '<tr><td colspan="7" style="text-align: center; padding: 25px; color: var(--text-muted);">No recommendations found in history yet.</td></tr>';
      return;
    }

    listBody.innerHTML = '';
    items.forEach(item => {
      const row = document.createElement('tr');
      const dateStr = new Date(item.createdAt).toLocaleDateString('en-IN', {
        month: 'short', day: 'numeric', year: 'numeric'
      });

      const badgeHtml = item.hasSoilTestReport
        ? '<span class="result-badge badge-calibrated" style="font-size: 0.7rem; padding: 3px 8px;">Soil Calibrated</span>'
        : '<span class="result-badge badge-uncalibrated" style="font-size: 0.7rem; padding: 3px 8px;">General Guide</span>';

      row.innerHTML = `
        <td><strong>#${item.id}</strong></td>
        <td>${dateStr}</td>
        <td>${item.farmerName} <br/><small style="color: var(--text-muted);">${item.location}</small></td>
        <td><strong>${item.cropName}</strong> (${item.farmSizeAcre} ac)</td>
        <td>${item.growthStageName || item.growthStage}</td>
        <td>${badgeHtml}</td>
        <td>
          <button class="btn btn-secondary" style="padding: 6px 12px; font-size: 0.8rem;" onclick="App.viewHistoricalDetails(${item.id})">View Details</button>
          <button class="btn btn-danger" style="padding: 6px 10px; font-size: 0.8rem; margin-left: 6px;" onclick="App.deleteHistoryItem(${item.id})">✕</button>
        </td>
      `;
      listBody.appendChild(row);
    });
  },

  filterHistory(searchTerm) {
    const term = (searchTerm || '').toLowerCase().trim();
    if (!term) {
      this.renderHistoryTable(this.state.historyList);
      return;
    }

    const filtered = this.state.historyList.filter(item =>
      (item.farmerName && item.farmerName.toLowerCase().includes(term)) ||
      (item.cropName && item.cropName.toLowerCase().includes(term)) ||
      (item.location && item.location.toLowerCase().includes(term))
    );
    this.renderHistoryTable(filtered);
  },

  async viewHistoricalDetails(id) {
    try {
      const res = await Api.getRecommendationById(id);
      this.renderRecommendationResult(res.data);
      this.switchView('result');
    } catch (err) {
      this.showToast('Could not load recommendation details', 'error');
    }
  },

  async deleteHistoryItem(id) {
    if (!confirm(`Are you sure you want to delete recommendation #${id}?`)) return;

    try {
      await Api.deleteRecommendation(id);
      this.showToast(`Recommendation #${id} deleted`, 'info');
      this.loadHistory();
    } catch (err) {
      this.showToast('Failed to delete item', 'error');
    }
  },

  renderKnowledgeBase() {
    const cropCards = document.getElementById('kbCropGrid');
    if (cropCards && this.state.crops) {
      cropCards.innerHTML = '';
      this.state.crops.forEach(c => {
        const card = document.createElement('div');
        card.className = 'card';
        card.style.marginBottom = '15px';
        card.innerHTML = `
          <div class="card-header">
            <div>
              <h3 style="color: var(--primary-dark); font-size: 1.15rem;">${c.cropName}</h3>
              <div style="font-size: 0.8rem; color: var(--text-muted); font-style: italic;">${c.scientificName || ''} • ${c.category}</div>
            </div>
            <span class="nutrient-rating-badge rating-medium">ICAR Standard</span>
          </div>
          <p style="font-size: 0.875rem; color: var(--text-secondary); margin-bottom: 12px;">${c.description || ''}</p>
          <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(140px, 1fr)); gap: 10px; background: #f8fafc; padding: 12px; border-radius: var(--radius-sm); font-size: 0.85rem;">
            <div><strong>Base RDF (N:P:K):</strong><br/>${c.baseNitrogenKgHa} : ${c.basePhosphorusKgHa} : ${c.basePotassiumKgHa} kg/ha</div>
            <div><strong>Optimum pH:</strong><br/>${c.optimumPhMin} - ${c.optimumPhMax}</div>
            <div><strong>FYM Baseline:</strong><br/>${c.organicManureTonsPerAcre} tons/acre</div>
            <div><strong>Preferred Soil:</strong><br/>${c.preferredSoilTypes}</div>
          </div>
        `;
        cropCards.appendChild(card);
      });
    }

    const fertTable = document.getElementById('kbFertilizerTable');
    if (fertTable && this.state.fertilizers) {
      fertTable.innerHTML = '';
      this.state.fertilizers.forEach(f => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td><strong>${f.fertilizerName}</strong></td>
          <td><span style="display: inline-block; padding: 2px 8px; border-radius: 4px; font-size: 0.75rem; background: #e0f2fe; color: #0369a1;">${f.fertilizerType}</span></td>
          <td>${f.nPercent}% N - ${f.pPercent}% P₂O₅ - ${f.kPercent}% K₂O</td>
          <td>${f.bagWeightKg} kg</td>
          <td><small>${f.description || '-'}</small></td>
        `;
        fertTable.appendChild(row);
      });
    }
  },

  renderFarmersDirectory() {
    const tableBody = document.getElementById('farmersTableBody');
    if (!tableBody || !this.state.farmers) return;

    tableBody.innerHTML = '';
    this.state.farmers.forEach(f => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td><strong>#${f.id}</strong></td>
        <td><strong>${f.name}</strong></td>
        <td>${f.phoneNumber || 'N/A'}</td>
        <td>${f.location}</td>
        <td>${f.farmSizeAcre} Acres</td>
        <td>
          <button class="btn btn-outline" style="padding: 5px 12px; font-size: 0.8rem;" onclick="App.selectFarmerForAdvice(${f.id})">Create Advice 🌾</button>
        </td>
      `;
      tableBody.appendChild(row);
    });
  },

  selectFarmerForAdvice(farmerId) {
    this.switchView('form');
    this.onFarmerSelected(farmerId);
    document.getElementById('existingFarmerSelect').value = farmerId;
  },

  showToast(message, type = 'info') {
    const container = document.getElementById('toastContainer');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `<span>${type === 'success' ? '✓' : type === 'error' ? '⚠️' : 'ℹ'}</span> <span>${message}</span>`;

    container.appendChild(toast);

    setTimeout(() => {
      toast.style.opacity = '0';
      toast.style.transition = 'opacity 0.5s ease';
      setTimeout(() => toast.remove(), 500);
    }, 4500);
  }
};

// Polyfill String.equalsIgnoreCase for convenience
String.prototype.equalsIgnoreCase = function (other) {
  return this.toLowerCase() === (other || '').toLowerCase();
};
