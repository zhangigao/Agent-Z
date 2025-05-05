# AgentX - 智能对话系统平台 [![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

**自然语言驱动的下一代 Agent 开发平台** | [在线演示](https://demo.agentx.ai) | [文档中心](https://docs.agentx.ai) | [社区讨论](https://github.com/yourname/agentx/discussions)

![AgentX 架构图](docs/images/architecture.png)

## 🚀 核心功能
- **自然语言驱动**  
  无需复杂流程设计，通过自然语言指令快速构建 Agent
- **多模型融合**  
  支持 GPT/GLM/Claude 等主流大模型，支持混合调度策略
- **知识增强**  
  基于 RAG 的文档理解能力，支持 PDF/Word/Markdown 多格式
- **开放生态**  
  提供插件市场、工具市场、知识库共享社区
- **企业级特性**  
  多租户支持、细粒度权限控制、审计日志

## 🛠️ 技术架构
### 分层架构（DDD）

src

├── interfaces    # 接口层：REST API

├── application   # 应用层：工作流编排、事务管理

├── domain        # 领域层：对话管理、知识库、计费系统

└── infrastructure # 基础设施：向量数据库、模型服务、缓存
### 技术栈
- **后端**: Java 17 / Spring Boot 3.1 / Spring Data JPA
- **AI 引擎**: LangChain4j / PGVector / Sentence Transformers
- **数据库**: PostgreSQL 14 + TimescaleDB（时序数据）
- **部署**: Docker Compose / Kubernetes（生产推荐）
- **监控**: Prometheus + Grafana + ELK

## ⚡ 快速启动
### 前置要求
- JDK 17+
- Docker 24.0+ & Docker Compose 2.20+
- 至少 8GB 可用内存

### 三步启动开发环境
```bash
# 1. 克隆仓库
git clone https://github.com/yourname/agentx.git
cd agentx

# 2. 配置环境变量 
cp .env.example .env
# 编辑 .env 文件填写你的模型 API KEY

# 3. 一键启动服务
docker-compose -f docker-compose.dev.yml up --build
```
访问以下服务：

- 前端界面：http://localhost:3000
- API 文档：http://localhost:8080/swagger-ui.html
- 监控看板：http://localhost:9090


## 🔧 配置说明
关键配置项（application.yml）：

```Yaml
agentx:
  llm:
    providers: 
      openai:
        base-url: ${OPENAI_BASE_URL:https://api.openai.com}
        api-key: ${OPENAI_API_KEY:}
        models: 
          - gpt-4-turbo
          - gpt-3.5-turbo
          
  knowledge:
    storage-type: pgvector # 可选 qdrant/milvus
    chunk-size: 1024
    overlap: 200
```

## 🤝 参与贡献
欢迎提交 Pull Request！请先阅读：

- [贡献指南]()
- [开发规范]()
- [路线图]()

推荐入门任务：
- [good first issue]()
- [文档改进]()

## 📜 许可证
Apache 2.0 License © 2025 [ZhangHongJun]
