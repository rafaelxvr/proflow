# Proflow 
Este é um aplicativo desenvolvido para auxiliar no planejamento e acompanhamento dos projetos da empresa, bem como na gestão de times, clientes, tarefas e atividades. Ele oferece funcionalidades para cadastrar clientes, projetos e atividades, além de permitir a associação de atividades a clientes e projetos específicos.
Funcionalidades Principais

    Cadastro de Clientes: Registre informações sobre os clientes da empresa, como nome, documento e email.

    Cadastro de Projetos: Cadastre projetos associados aos clientes. Informações como nome, descrição e outras podem ser armazenadas.

    Cadastro de Atividades: Adicione atividades para um cliente em um projeto específico. Essas atividades podem ser tarefas, marcos ou qualquer outra atividade relevante.

Configuração do Ambiente de Desenvolvimento
Backend (Java Spring Boot)

## Diagrama de Classes
![proflow drawio](https://github.com/rafaelxvr/proflow/assets/78372916/0bd7c246-7a36-432c-b944-caaf2538856b)

Arquivo em PDF para melhor visualização:
[proflow.drawio.pdf](https://github.com/rafaelxvr/proflow/files/12471036/proflow.drawio.pdf)

## Cobertura de Testes
![image](https://github.com/rafaelxvr/proflow/assets/78372916/756f62eb-dc9e-4969-be38-09fbba7ed1cb)

## Executar a Aplicação
Para executar o backend em Spring Boot, siga as etapas abaixo:

- Certifique-se de ter o Docker Engine instalado em sua máquina.
- Navegue para o diretório do backend.
- Execute o seguinte comando para criar e executar o contêiner do banco de dados: `docker-compose up -d`
- Agora você pode iniciar a aplicação Spring Boot normalmente.

O backend da aplicação deve estar apontando para a porta padrão em `http://localhost:8080/`

Para configurar o ambiente de desenvolvimento do frontend (Vue.js), siga as etapas abaixo:

- Certifique-se de ter o Node.js instalado em sua máquina.
- Navegue para o diretório do frontend dentro de `src/view`.
- Execute o seguinte comando para instalar as dependências do projeto: `npm install`

O frontend estará apontando para a porta padrâo `http://localhost:5173/`

Após a instalação das dependências, você pode iniciar o servidor de desenvolvimento com o seguinte comando: `npm run dev`

## Pacotes e Dependências (Vue.js)

O arquivo package.json do projeto Vue.js inclui as seguintes dependências principais:

    vue: Biblioteca principal do Vue.js.
    vue-router: Gerenciador de rotas para aplicativos Vue.js.
    vuex: Biblioteca de gerenciamento de estado para aplicativos Vue.js.

Além disso, também inclui as dependências necessárias para linting, formatação e compilação do código:

    eslint: Ferramenta de linting para identificar e corrigir problemas de código.
    eslint-plugin-vue: Plugin para linting específico do Vue.js.
    prettier: Ferramenta para formatação consistente do código.
    vite: Ambiente de desenvolvimento rápido para aplicativos Vue.js.

## Licença
Este projeto é licenciado sob a MIT License.
