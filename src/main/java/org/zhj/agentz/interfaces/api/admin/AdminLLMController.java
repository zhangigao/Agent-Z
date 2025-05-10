package org.zhj.agentz.interfaces.api.admin;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhj.agentz.application.admin.AdminLLMAppService;
import org.zhj.agentz.application.llm.dto.ModelDTO;
import org.zhj.agentz.application.llm.dto.ProviderDTO;
import org.zhj.agentz.infrastructure.auth.UserContext;
import org.zhj.agentz.interfaces.api.common.Result;
import org.zhj.agentz.interfaces.dto.llm.ModelCreateRequest;
import org.zhj.agentz.interfaces.dto.llm.ModelUpdateRequest;
import org.zhj.agentz.interfaces.dto.llm.ProviderCreateRequest;
import org.zhj.agentz.interfaces.dto.llm.ProviderUpdateRequest;

/**
 * 管理员LLM管理
 */
@RestController
@RequestMapping("/admin/llm")
public class AdminLLMController {

    private final AdminLLMAppService adminLLMAppService;

    public AdminLLMController(AdminLLMAppService adminLLMAppService) {
        this.adminLLMAppService = adminLLMAppService;
    }

    /**
     * 创建服务商
     * @param request 请求对象
     */
    @PostMapping("")
    public Result<ProviderDTO> createProvider(@RequestBody @Validated ProviderCreateRequest request){
        String userId = UserContext.getCurrentUserId();
        return Result.success(adminLLMAppService.createProvider(request, userId));
    }

    /**
     * 更新服务商
     * @param id 服务商id
     * @param request 请求对象
     */
    @PutMapping("/{id}")
    public Result<ProviderDTO> updateProvider(@PathVariable String id, @RequestBody @Validated ProviderUpdateRequest request){
        String userId = UserContext.getCurrentUserId();
        request.setId(id);
        return Result.success(adminLLMAppService.updateProvider(request, userId));
    }

    /**
     * 删除服务商
     * @param id 服务商id
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteProvider(@PathVariable String id){
        String userId = UserContext.getCurrentUserId();
        adminLLMAppService.deleteProvider(id, userId);
        return Result.success();
    }

    /**
     * 创建模型
     * @param request 请求对象
     */
    @PostMapping("/model")
    public Result<ModelDTO> createModel(@RequestBody @Validated ModelCreateRequest request){
        String userId = UserContext.getCurrentUserId();
        return Result.success(adminLLMAppService.createModel(request, userId));
    }

    /**
     * 更新模型
     * @param id 更新的id
     * @param request 请求对象
     */
    @PutMapping("/model/{id}")
    public Result<ModelDTO> updateModel(@PathVariable String id, @RequestBody @Validated ModelUpdateRequest request){
        String userId = UserContext.getCurrentUserId();
        request.setId(id);
        return Result.success(adminLLMAppService.updateModel(request, userId));
    }


    /**
     * 删除模型
     * @param id 模型id
     */
    @DeleteMapping("/model/{id}")
    public Result<Void> deleteModel(@PathVariable String id){
        String userId = UserContext.getCurrentUserId();
        adminLLMAppService.deleteModel(id, userId);
        return Result.success();
    }
}