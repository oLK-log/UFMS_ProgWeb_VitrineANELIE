# UFMS_ProgWeb_VitrineANELIE
Repositório contendo o código-fonte, diagramas e artefatos do sistema "Vitrine ANELIE", desenvolvido ao longo da disciplina de Programação Web.

* **Faculdade:** UFMS
* **Curso:** Engenharia de Software
* **Tecnologias e Ferramentas:**
  - Java 17+
  - Spring Boot (Spring Web, Spring Data JPA)
  - Banco de Dados (PostgreSQL / H2)
  - HTML5, CSS3 e JavaScript

### Descrição simplificada:
O sistema Vitrine ANELIE é uma aplicação web focada em simplificar o processo de escolha de produtos e montagem de pedidos para os clientes da empresa ANELIE. O sistema possui dois fluxos principais de interação:

* **Visão do Cliente:** O usuário pode acessar o catálogo de produtos de forma livre, sem a necessidade de autenticação (login). Ele pode navegar, escolher os itens desejados e, ao final, gerar um resumo de pedido em formato de texto. Esse texto é formatado para ser facilmente copiado e colado pelo cliente em aplicativos de mensagens (como WhatsApp) para finalizar a compra com a loja.
* **Visão do Vendedor (Administrativo):** Uma área restrita onde o administrador do sistema pode realizar o gerenciamento do catálogo. Nesta área, o vendedor realiza o CRUD (Cadastro, Leitura, Atualização e Exclusão) dos produtos que serão exibidos na vitrine virtual.

### Modelagem e Arquitetura:
Este projeto adota o padrão arquitetural MVC (Model-View-Controller), separando claramente as responsabilidades de persistência de dados, regras de negócio e interface de usuário.

* #### **Diagrama Entidade-Relacionamento (DER)**
<div align="center">
  <img src="./docs/Diagramas/Diagrama EER - Grupo Catálogo de Jóias.jpg" alt="Imagem do Diagrama Entidade-Relacionamento" width="700"/>
</div>

* #### **Mockups de Interface (Wireframes)**
<div align="center">
  <img src="./docs/Diagramas/mockup_telas.jpg" alt="Imagem dos Mockups da Interface" width="700"/>
</div>

* #### **Diagrama de Classes (Camada Model)**
<div align="center">
  <img src="./docs/Diagramas/Diagrama de Classes - Grupo Catálogo de Jóias.jpg" alt="Imagem do Diagrama de Classes" width="700"/>
</div>

⚠ **Atenção**: Material com fins de aprendizado acadêmico, e assim sendo, pode conter **erros** e **inconsistências**.

* ### **Links e material de apoio** 📖
 - [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
 - [Padrão MVC e Spring](https://www.baeldung.com/spring-mvc-tutorial)
 - [Guia de HTML5 e CSS3 - MDN](https://developer.mozilla.org/pt-BR/docs/Web)
