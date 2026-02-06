# viviannesantiagocavalcantedeavila737672
####Projeto Pratico para o processo seletivo SEPLAG-MT

#########PROJETO PRÁTICO - IMPLEMENTAÇÃO BACK END JAVA SÊNIOR

# Music Manager API

API RESTful desenvolvida para gerenciamento de catálogos musicais e integração com bases regionais, demonstrando práticas de arquitetura distribuída, segurança e escalabilidade.

## 🏗 Arquitetura e Decisões Técnicas

O projeto foi construído seguindo os princípios de **Clean Architecture** (simplificada para o escopo) e **12-Factor App**.

### 1. Stack Tecnológica
* **Java 21 + Spring Boot 3**: Escolhidos pela robustez, suporte a *Virtual Threads* e ecossistema maduro para aplicações corporativas.
* **PostgreSQL**: Banco relacional robusto para garantir integridade referencial dos dados (ACID).
* **Flyway**: Utilizado para *Database Migration*, garantindo que a evolução do esquema do banco seja versionada e reprodutível em qualquer ambiente.
* **MinIO (S3 Compatible)**: Solução de *Object Storage* para armazenar imagens. Simula um ambiente de nuvem real (AWS S3) localmente, desacoplando o armazenamento de arquivos do servidor de aplicação (Stateless).
* **Bucket4j**: Implementação de *Rate Limiting* através do algoritmo *Token Bucket*, protegendo a API contra abusos (DDoS ou excesso de uso).

### 2. Modelagem de Dados
A estrutura foi normalizada até a 3ª Forma Normal (3FN) para evitar redundâncias:
* **Artist vs Album**: Separação clara com relação 1:N. O campo \`type\` na entidade Artist permite polimorfismo simples nas consultas.
* **Regional (Sincronização)**: A tabela \`regional\` possui uma chave primária interna (\`internal_id\`) separada do ID externo. Isso permite o versionamento histórico dos dados.
    * *Estratégia Append-Only*: Em vez de sobrescrever registros (UPDATE), o sistema inativa a versão anterior e insere uma nova. Isso garante auditabilidade completa das mudanças vindas da API externa.

### 3. Estratégia de Sincronização (Performance)
O algoritmo de sincronização de Regionais foi otimizado para **Complexidade O(n)**:
1.  Carrega todo o estado "Ativo" local em memória (HashMap).
2.  Itera sobre a resposta da API externa uma única vez.
3.  Detecta *Inserts*, *Updates* (versionamento) e *Deletes* (inativação lógica) sem a necessidade de múltiplas consultas ao banco dentro do loop (N+1 problem evitado).

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* Docker & Docker Compose
* Java 17 ou superior
* Maven

### Passo a Passo

1.  **Subir a Infraestrutura** (Banco de Dados e MinIO):
    Na raiz do projeto, execute:
    \`\`\`bash
    docker compose up -d
    \`\`\`
    *Aguarde alguns instantes para que o script de inicialização do MinIO crie o bucket automaticamente.*

2.  **Compilar e Rodar a Aplicação**:
    \`\`\`bash
    mvn spring-boot:run
    \`\`\`

3.  **Acessar a Documentação (Swagger/OpenAPI)**:
    Abra no navegador: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🛡 Segurança e Rate Limiting

* **Autenticação**: JWT (Stateless) com expiração curta (5 minutos) para alta segurança.
* **CORS**: Configurado para bloquear requisições de origens não autorizadas (bloqueio de domínios externos).
* **Rate Limit**: Limitado a **10 requisições por minuto** por IP ou Usuário, retornando \`HTTP 429 Too Many Requests\` se excedido.

## 🧪 Testes

O projeto inclui testes unitários focados nas regras de negócio críticas (Sincronização) utilizando **JUnit 5** e **Mockito**.
Para executar os testes:
\`\`\`bash
mvn test
\`\`\`

---
*Desenvolvido por Vivianne Santiago Cavalcante de Avila*