# ExcelToDB: Transferindo dados da planilha para o Banco de dados
A pouco tempo o **Governo do Reino Unido** foi alvo de críticas por **deixar de registrar mais de 15 mil novos casos de COVID-19** no país e consequentemente deixou de avisar a milhões de portadores do SARS-CoV-2 para ficarem em quarentena. A causa da falha? **Uma limitação na planilha do Excel usada para registrar os dados** [Se não sabia disso dá uma lida aqui](https://gizmodo.uol.com.br/limitacao-excel-reino-unido-covid-19/).

A notícia acima pode parecer inacreditável a primeira vista, mas eu já trabalhei numa empresa que gerenciava um importante processo interno por meio de uma planilha do excel, e por isso não dúvido que a mesma situação ocorra em outras empresas. Apesar da flexibilidade do Excel, ele possui suas limitações, e devemos considerar que um sistema dedicado pode trazer muito mais flexibilidade e confiabilidade do que uma planilha.

Apesar de parecer óbvio, precisamos levar em consideração que **migrar centenas ou talvez milhares de dados de uma planilha para um banco de dados está longe de ser uma tarefa fácil** e dependendo do volume de dados é inviável de fazer isso manualmente.

Por isso, criei um programa em java que **faz a leitura de uma planilha no formato .xlsx** e possibilita a opção de **salvar direto no banco de dados e criar o script correspondente às operações realizadas e o script para desfazer essas operações** (que pode ser muito útil) ou apenas **gerar o script SQL para inserir e desfazer a operação de inserir**.

