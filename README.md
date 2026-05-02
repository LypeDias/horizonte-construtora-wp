# Horizonte Construtora — Plataforma Web

Projeto desenvolvido utilizando WordPress com foco na apresentação institucional de uma construtora, incluindo páginas informativas, sistema de exibição de empreendimentos, formulário de contato e base para futura expansão com autenticação de usuários e API própria.

---

## Funcionalidades Implementadas

### Estrutura Global
- Header com logo e menu interativo (Home, Empreendimentos, Sobre, Contato)
- Botão de acesso rápido via WhatsApp (redirecionamento para atendimento)
- Botão de Login (redireciona para página de autenticação)
- Footer estruturado com:
  - Logo e frase institucional
  - Links rápidos para navegação principal
  - Seção de contato (telefone, e-mail e localização)
  - Links de filtro para empreendimentos (em desenvolvimento)
  - Ícones de redes sociais

---

## Páginas do Projeto

### Home (90% concluída)
- Banner principal
- Seção de empreendimentos em destaque (cards dinâmicos vinculados aos posts)
- Botão para listagem geral de empreendimentos
- Seção de métricas/resultados da empresa
- Botão "Conheça-nos" (redireciona para Sobre)
- Seção de contato com botão direto para WhatsApp
- Formulário de contato integrado com envio de mensagens
- Integração com Google Maps
- Em desenvolvimento: barra de filtro de empreendimentos

---

### Empreendimentos (em desenvolvimento)
- Banner principal implementado
- Estrutura base criada
- Desenvolvimento do sistema de listagem e filtros em andamento

---

### Sobre (100% concluída)
- Banner principal com identidade visual e frase de impacto
- História da empresa
- Missão, Visão e Valores
- Seção de valores institucionais
- Resultados e números da empresa
- Formulário de contato
- Integração com Google Maps
- Animações aplicadas em elementos textuais

---

### Contato (100% concluída)
- Banner principal com identidade visual
- Formulário de contato funcional
- Seção com incentivo ao contato
- Ícones de redes sociais
- Integração com Google Maps

---

### Login (em desenvolvimento)
- Estrutura inicial da interface criada
- Em análise:
  - Implementação via plugin ou API própria
- Futuro sistema de autenticação com diferenciação entre:
  - Cliente
  - Administrador

---

## Conteúdo e Gestão de Empreendimentos

- Criação de posts para todos os empreendimentos
- Desenvolvimento do post modelo baseado no empreendimento de Florianópolis
- Objetivo: padronizar estrutura para facilitar replicação dos demais imóveis

---

## Plugins Utilizados

- Elementor
- MetForm
- Search & Filter
- Ultimate Addons for Elementor (UAE)
- Custom Post Type UI
- Click to Chat
- WP Mail SMTP
- Yoast SEO

---

## Observações sobre SEO

O plugin Yoast SEO foi utilizado para otimização completa do site, permitindo:

- Otimização de títulos e meta descriptions
- Análise de palavras-chave e legibilidade
- Configuração de Search Appearance das páginas principais
- Melhoria de indexação em mecanismos de busca
- Aplicação de boas práticas de SEO on-page

---

## Tecnologias Utilizadas

- WordPress
- Elementor
- PHP
- HTML, CSS e JavaScript
- Integrações com Google Maps e WhatsApp

---

## Arquitetura do Projeto

O repositório contém a exportação do WordPress:

- export.xml

---

## API (Em Desenvolvimento)

O projeto está evoluindo para uma arquitetura híbrida com API própria, com objetivo de:

- Gerenciar autenticação de usuários (Login/Logout)
- Diferenciar perfis de acesso (Cliente e Administrador)
- Alimentar dados dinâmicos das áreas internas do sistema
- Suportar futuras funcionalidades de painel administrativo e gestão

---

## Como Executar o Projeto

1. Instalar WordPress localmente ou em servidor
2. Instalar o tema Hello Elementor
3. Instalar os plugins listados acima
4. Acessar o painel administrativo
5. Importar o arquivo export.xml via Ferramentas → Importar

---

## Status do Projeto

Em desenvolvimento ativo

---

## Autor

Projeto desenvolvido como parte de estudos em desenvolvimento web com WordPress, com foco em construção de soluções reais para o setor imobiliário.