# Proflow 
Este é um aplicativo desenvolvido para auxiliar no planejamento e acompanhamento dos projetos da empresa, bem como na gestão de times, clientes, tarefas e atividades. Ele oferece funcionalidades para cadastrar clientes, projetos e atividades, além de permitir a associação de atividades a clientes e projetos específicos.
Funcionalidades Principais

    Cadastro de Clientes: Registre informações sobre os clientes da empresa, como nome, documento e email.

    Cadastro de Projetos: Cadastre projetos associados aos clientes. Informações como nome, descrição e outras podem ser armazenadas.

    Cadastro de Atividades: Adicione atividades para um cliente em um projeto específico. Essas atividades podem ser tarefas, marcos ou qualquer outra atividade relevante.

Configuração do Ambiente de Desenvolvimento
Backend (Java Spring Boot)

## Diagrama de Classes
![image](https://github.com/rafaelxvr/proflow/assets/78372916/92d27aea-e75d-4c4d-aee3-6c813a94c54c)

Para executar o backend da aplicação, siga as etapas abaixo:

    Certifique-se de ter o Docker Engine instalado em sua máquina.
    Navegue para o diretório do backend.
    Execute o seguinte comando para criar e executar o contêiner do banco de dados:

    docker-compose up -d

    Agora você pode iniciar a aplicação Spring Boot normalmente.

Frontend (Vue.js)

Para configurar o ambiente de desenvolvimento do frontend, siga as etapas abaixo:

    Certifique-se de ter o Node.js instalado em sua máquina.
    Navegue para o diretório do frontend.
    Execute o seguinte comando para instalar as dependências do projeto:

npm install

Após a instalação das dependências, você pode iniciar o servidor de desenvolvimento com o seguinte comando:

arduino

    npm run dev

Pacotes e Dependências (Vue.js)

O arquivo package.json do projeto Vue.js inclui as seguintes dependências principais:

    vue: Biblioteca principal do Vue.js.
    vue-router: Gerenciador de rotas para aplicativos Vue.js.
    vuex: Biblioteca de gerenciamento de estado para aplicativos Vue.js.

Além disso, também inclui as dependências necessárias para linting, formatação e compilação do código:

    eslint: Ferramenta de linting para identificar e corrigir problemas de código.
    eslint-plugin-vue: Plugin para linting específico do Vue.js.
    prettier: Ferramenta para formatação consistente do código.
    vite: Ambiente de desenvolvimento rápido para aplicativos Vue.js.

Licença

Este projeto é licenciado sob a MIT License.
