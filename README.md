# ExcelToDB: Transferindo dados da Planilha para o Banco de Dados
A pouco tempo o **Governo do Reino Unido** foi alvo de críticas por **deixar de registrar mais de 15 mil novos casos de COVID-19** no país e consequentemente deixou de avisar a milhões de portadores do SARS-CoV-2 para ficarem em quarentena. A causa da falha? **Uma limitação na planilha do Excel usada para registrar os dados**. [Se não sabia disso dá uma lida aqui](https://gizmodo.uol.com.br/limitacao-excel-reino-unido-covid-19/).

A notícia acima pode parecer inacreditável a primeira vista, mas eu já trabalhei numa empresa que gerenciava um importante processo interno por meio de uma planilha do excel, e por isso não dúvido que a mesma situação ocorra em outras empresas. Apesar da flexibilidade do Excel, ele possui suas limitações, e devemos considerar que um sistema dedicado pode trazer muito mais flexibilidade e confiabilidade do que uma planilha.

Apesar de parecer óbvio, precisamos levar em consideração que **migrar centenas ou talvez milhares de dados de uma planilha para um banco de dados está longe de ser uma tarefa fácil** e dependendo do volume de dados é inviável fazer isso manualmente.

Por isso, criei um programa em java que **faz a leitura de uma planilha no formato .xlsx** e possibilita a opção de **salvar direto no banco de dados e criar o script correspondente às operações realizadas e o script para desfazer essas operações** (que pode ser muito útil) ou apenas **gerar o script SQL para inserir e desfazer a operação de inserir**.
 ![](https://github.com/fabioTowers/ExcelToDB/blob/main/ilustracao_ExcelToDB.jpg)

Veja abaixo as janelas do programa:

Utilizei nesse projeto alguns **Design Patterns como Gateway, MVC e DAO**, tudo foi feito em um projeto **Maven** no **Eclipse** e o *framework* utilizado para ler a planilha foi o **Apache POI**, a interface gráfica foi feita com **Java Swing**.

Esse projeto nasceu de uma necessidade pessoal minha: utilizo uma planilha do Google para controlar as finanças e conforme fui evoluindo na faculdade e aprendi a usar Bancos de Dados resolvi que seria legal manter um histórico das informações mensais no banco, o resultado você vê nesse projeto.
Para ficar mais claro o funcionamento do programa deixei uma cópia da planilha que inspirou esse projeto preenchida com dados fictícios para exemplificar e você pode dar uma olhada nela [nesse link](https://docs.google.com/spreadsheets/d/1kM-nf5Hjwc83KpomDjvyMolrkByd4O-8A1gPx5RV3P4/edit?usp=sharing). Cada uma das tabelas nessa planilha é armazenada em uma tabela de mesmo nome no banco de dados.

Se gostou desse projeto não deixei de conhecer meus outros projetos me seguindo nas minhas redes sociais:

[**Veja meu perfil no Medium**](https://medium.com/@fabiomendes_95615)

[**Veja meu perfil no LinkedIn**](https://www.linkedin.com/in/fabio-mendes-35743b128)
