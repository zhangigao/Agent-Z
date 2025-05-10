package org.zhj.agentz.application.llm.service;

import org.springframework.stereotype.Service;
import org.zhj.agentz.application.llm.assembler.ModelAssembler;
import org.zhj.agentz.application.llm.assembler.ProviderAssembler;
import org.zhj.agentz.application.llm.dto.ModelDTO;
import org.zhj.agentz.application.llm.dto.ProviderDTO;
import org.zhj.agentz.domain.llm.model.ModelEntity;
import org.zhj.agentz.domain.llm.model.ProviderAggregate;
import org.zhj.agentz.domain.llm.model.ProviderEntity;
import org.zhj.agentz.domain.llm.model.enums.ModelType;
import org.zhj.agentz.domain.llm.model.enums.ProviderType;
import org.zhj.agentz.domain.llm.service.LLMDomainService;
import org.zhj.agentz.infrastructure.entity.Operator;
import org.zhj.agentz.infrastructure.llm.protocol.enums.ProviderProtocol;
import org.zhj.agentz.interfaces.dto.llm.ModelCreateRequest;
import org.zhj.agentz.interfaces.dto.llm.ModelUpdateRequest;
import org.zhj.agentz.interfaces.dto.llm.ProviderCreateRequest;
import org.zhj.agentz.interfaces.dto.llm.ProviderUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LLMAppService {

    private final LLMDomainService llmDomainService;

    public LLMAppService(LLMDomainService llmDomainService) {
        this.llmDomainService = llmDomainService;
    }

    /**
     * 获取服务商聚合根
     * @param providerId 服务商id
     * @param userId 用户id
     * @return ProviderAggregate
     */
    public ProviderDTO getProviderDetail(String providerId, String userId) {
        ProviderAggregate providerAggregate = llmDomainService.getProviderAggregate(providerId, userId);
        return ProviderAssembler.toDTO(providerAggregate);
    }

    /**
     * 创建服务商
     * @param providerCreateRequest 请求对象
     * @param userId 用户id
     * @return ProviderDTO
     */
    public ProviderDTO createProvider(ProviderCreateRequest providerCreateRequest,String userId) {
        ProviderEntity provider = ProviderAssembler.toEntity(providerCreateRequest, userId);
        provider.setIsOfficial(false);
        llmDomainService.createProvider(provider);
        return ProviderAssembler.toDTO(provider);
    }

    /**
     * 更新服务商
     * @param providerUpdateRequest 更新对象
     * @param userId 用户id
     * @return ProviderDTO
     */
    public ProviderDTO updateProvider(ProviderUpdateRequest providerUpdateRequest, String userId) {
        ProviderEntity provider = ProviderAssembler.toEntity(providerUpdateRequest, userId);
        llmDomainService.updateProvider(provider);
        return ProviderAssembler.toDTO(provider);
    }


    /**
     * 获取服务商
     * @param providerId 服务商id
     * @return ProviderDTO
     */
    public ProviderDTO getProvider(String providerId, String userId) {
        ProviderEntity provider = llmDomainService.getProvider(providerId,userId);
        return ProviderAssembler.toDTO(provider);
    }

    /**
     * 删除服务商
     * @param providerId 服务商id
     * @param userId 用户id
     */
    public void deleteProvider(String providerId, String userId) {
        llmDomainService.deleteProvider(providerId,userId, Operator.USER);
    }

    /**
     * 获取用户自己的服务商
     * @param userId 用户id
     * @return List<ProviderDTO>
     */
    public List<ProviderDTO> getUserProviders(String userId) {
        List<ProviderAggregate> providers = llmDomainService.getUserProviders(userId);
        return providers.stream().map(ProviderAssembler::toDTO).collect(Collectors.toList());
    }

    /**
     * 获取所有服务商（包含官方和用户自定义）
     * @param userId 用户ID
     * @return 服务商DTO列表
     */
    public List<ProviderDTO> getAllProviders(String userId) {
        List<ProviderAggregate> providers = llmDomainService.getAllProviders(userId);
        return providers.stream().map(ProviderAssembler::toDTO).collect(Collectors.toList());
    }

    /**
     * 获取官方服务商
     * @return 官方服务商DTO列表
     */
    public List<ProviderDTO> getOfficialProviders() {
        List<ProviderAggregate> providers = llmDomainService.getOfficialProviders();
        return providers.stream().map(ProviderAssembler::toDTO).collect(Collectors.toList());
    }

    /**
     * 获取用户自定义服务商
     * @param userId 用户ID
     * @return 用户自定义服务商DTO列表
     */
    public List<ProviderDTO> getCustomProviders(String userId) {
        List<ProviderAggregate> providers = llmDomainService.getCustomProviders(userId);
        return providers.stream().map(ProviderAssembler::toDTO).collect(Collectors.toList());
    }

    /**
     * 获取用户服务商协议
     * @return List<ProviderProtocol>
     */
    public List<ProviderProtocol> getUserProviderProtocols() {
        return llmDomainService.getProviderProtocols();
    }

    /**
     * 创建模型
     * @param modelCreateRequest 请求对象
     * @param userId 用户id
     * @return ModelDTO
     */
    public ModelDTO createModel(ModelCreateRequest modelCreateRequest, String userId) {
        ModelEntity model = ModelAssembler.toEntity(modelCreateRequest, userId);
        // 用户创建默认是非官方
        model.setOfficial(false);
        llmDomainService.checkProviderExists(modelCreateRequest.getProviderId(),userId);
        llmDomainService.createModel(model);
        return ModelAssembler.toDTO(model);
    }

    /**
     * 修改模型
     * @param modelUpdateRequest 请求对象
     * @param userId 用户id
     * @return ModelDTO
     */
    public ModelDTO updateModel(ModelUpdateRequest modelUpdateRequest, String userId) {
        ModelEntity model = ModelAssembler.toEntity(modelUpdateRequest, userId);
        llmDomainService.updateModel(model);
        return ModelAssembler.toDTO(model);
    }

    /**
     * 删除模型
     * @param modelId 模型id
     * @param userId 用户id
     */
    public void deleteModel(String modelId, String userId) {
        llmDomainService.deleteModel(modelId, userId,Operator.ADMIN);
    }

    /**
     * 修改模型状态
     * @param modelId 模型id
     * @param userId 用户id
     */
    public void updateModelStatus(String modelId, String userId) {
        llmDomainService.updateModelStatus(modelId, userId);
    }

    /**
     * 根据类型获取服务商
     * @param providerType 服务商类型编码：all-所有，official-官方，user-用户的
     * @param userId 用户ID
     * @return 服务商DTO列表
     */
    public List<ProviderDTO> getProvidersByType(ProviderType providerType, String userId) {
        List<ProviderAggregate> providers = llmDomainService.getProvidersByType(providerType, userId);
        return providers.stream().map(ProviderAssembler::toDTO).collect(Collectors.toList());
    }

    /**
     * 修改服务商状态
     * @param providerId 服务商id
     * @param userId 用户id
     */
    public void updateProviderStatus(String providerId, String userId) {
        llmDomainService.updateProviderStatus(providerId,userId);
    }

    /**
     * 获取所有激活模型
     * @param providerType 服务商类型
     * @param userId 用户id
     * @param modelType 模型类型（可选）
     * @return 模型列表
     */
    public List<ModelDTO> getActiveModelsByType(ProviderType providerType, String userId, ModelType modelType) {
        return llmDomainService.getProvidersByType(providerType, userId).stream()
                .filter(ProviderAggregate::getStatus)
                .flatMap(provider -> provider.getModels().stream())
                .filter(model -> modelType == null || model.getType() == modelType)
                .map(ModelAssembler::toDTO)
                .collect(Collectors.toList());
    }
}
