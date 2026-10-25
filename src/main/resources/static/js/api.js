/**
 * VAYAL – SMART FERTILIZER ADVISOR: API CLIENT
 * Handles asynchronous REST communication with the Spring Boot backend.
 */

const API_BASE = '/api';

const Api = {
  /**
   * Universal fetch helper with error unwrapping and status handling
   */
  async request(endpoint, options = {}) {
    const url = `${API_BASE}${endpoint}`;
    const defaultHeaders = {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    };

    const config = {
      ...options,
      headers: {
        ...defaultHeaders,
        ...options.headers
      }
    };

    try {
      const response = await fetch(url, config);
      const data = await response.json();

      if (!response.ok) {
        // Backend ApiError structure
        const errorMessage = data.message || (data.fieldErrors ? Object.values(data.fieldErrors).join(', ') : 'API request failed');
        const error = new Error(errorMessage);
        error.status = response.status;
        error.details = data;
        throw error;
      }

      return data;
    } catch (err) {
      console.error(`API Error on [${options.method || 'GET'}] ${endpoint}:`, err);
      throw err;
    }
  },

  // Farmers Endpoints
  async getFarmers() {
    return this.request('/farmers');
  },

  async getFarmerById(id) {
    return this.request(`/farmers/${id}`);
  },

  async createFarmer(farmerData) {
    return this.request('/farmers', {
      method: 'POST',
      body: JSON.stringify(farmerData)
    });
  },

  // Crops Endpoints
  async getCrops() {
    return this.request('/crops');
  },

  async getCropByCode(code) {
    return this.request(`/crops/code/${encodeURIComponent(code)}`);
  },

  // Fertilizers Endpoints
  async getFertilizers() {
    return this.request('/fertilizers');
  },

  // Guidance Rules
  async getGuidanceRules() {
    return this.request('/guidance-rules');
  },

  async getGuidanceRulesByCrop(cropCode) {
    return this.request(`/guidance-rules/crop/${encodeURIComponent(cropCode)}`);
  },

  // Recommendations
  async generateRecommendation(requestData) {
    return this.request('/recommendations', {
      method: 'POST',
      body: JSON.stringify(requestData)
    });
  },

  async getRecommendations() {
    return this.request('/recommendations');
  },

  async getRecommendationById(id) {
    return this.request(`/recommendations/${id}`);
  },

  async deleteRecommendation(id) {
    return this.request(`/recommendations/${id}`, {
      method: 'DELETE'
    });
  }
};
