package com.vayal.fertilizeradvisor.service.impl;

import com.vayal.fertilizeradvisor.entity.GuidanceRule;
import com.vayal.fertilizeradvisor.repository.GuidanceRuleRepository;
import com.vayal.fertilizeradvisor.service.GuidanceRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GuidanceRuleServiceImpl implements GuidanceRuleService {

    private final GuidanceRuleRepository guidanceRuleRepository;

    public GuidanceRuleServiceImpl(GuidanceRuleRepository guidanceRuleRepository) {
        this.guidanceRuleRepository = guidanceRuleRepository;
    }

    @Override
    public List<GuidanceRule> getRulesByCropCode(String cropCode) {
        return guidanceRuleRepository.findByCropCodeIgnoreCase(cropCode.trim());
    }

    @Override
    public List<GuidanceRule> getAllRules() {
        return guidanceRuleRepository.findAll();
    }
}
