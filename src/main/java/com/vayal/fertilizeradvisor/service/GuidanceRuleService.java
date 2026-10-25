package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.entity.GuidanceRule;

import java.util.List;

public interface GuidanceRuleService {
    List<GuidanceRule> getRulesByCropCode(String cropCode);
    List<GuidanceRule> getAllRules();
}
