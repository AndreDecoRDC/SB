# StudyBridge: Plataforma de Conexão entre Estudantes e Monitores Voluntários
O **StudyBridge** é uma plataforma online que conecta estudantes do ensino fundamental e médio a monitores voluntários que oferecem reforço escolar gratuito.  
O sistema permite que alunos encontrem apoio em disciplinas específicas, de acordo com a disponibilidade e localização dos monitores.

---

## Equipe de Desenvolvimento
| Ordem | Nome       |
|:------|:-----------|
| 1     | André      |
| 2     | Beatriz    |
| 3     | Bernardo   |
| 4     | Guilherme  |
| 5     | Lilith     |

---

## Atores do Sistema
| Ator          | Definição                                                                                               |
|:--------------|:--------------------------------------------------------------------------------------------------------|
| Estudante     | Usuário que procura reforço escolar gratuito em disciplinas específicas.                                |
| Monitor       | Usuário que oferece aulas de reforço gratuitamente, informando suas áreas de atuação e disponibilidade. |
| Administrador | Usuário responsável pela gestão de usuários, controle de conteúdo e manutenção da plataforma.           |

---

## Requisitos Funcionais
| Id     | Ator                | Descrição                                                                                                  |
|:-------|:--------------------|:-----------------------------------------------------------------------------------------------------------|
| REQ001 | Todos               | Realizar cadastro com verificação por e-mail.                                                              |
| REQ002 | Todos               | Realizar login com autenticação em duas etapas (2FA por e-mail).                                           |
| REQ003 | Estudante e Monitor | Inserir e editar dados pessoais de perfil.                                                                 |
| REQ004 | Monitor             | Gerenciar horários disponíveis (cadastrar, editar, remover).                                               |
| REQ005 | Estudante e Monitor | Gerenciar aulas marcadas (consultar, reagendar, cancelar).                                                 |
| REQ006 | Estudante           | Buscar monitores com filtros (disciplina, ordenação por ordem alfabética etc.).                            |
| REQ007 | Todos               | Redefinir senha via e-mail de verificação.                                                                 |
| REQ008 | Estudante           | Solicitar aula a um monitor.                                                                               |
| REQ009 | Monitor             | Confirmar ou recusar solicitações de aula.                                                                 |
| REQ010 | Estudante e Monitor | Avaliar o outro usuário após a aula (nota + comentário).                                                   |
| REQ011 | Administrador       | Gerenciar usuários (suspender/reativar contas).                                                            |
| REQ012 | Administrador       | Visualizar e gerenciar denúncias.                                                                          |
| REQ013 | Estudante e Monitor | Consultar histórico de aulas realizadas.                                                                   |
| REQ014 | Todos               | Visualizar perfis de outros usuários.                                                                      |
| REQ015 | Estudante e Monitor | Enviar denúncias sobre usuários.                                                                           |
| REQ016 | Todos               | Receber notificações internas (aulas, avaliações, comentários, confirmações, cancelamentos etc.).          |
| REQ017 | Administrador       | Buscar usuários com filtros (tipo, status, nome, etc.).                                                    |

---

## Regras de Negócio
| Id    | Nome                          | Descrição                                                                                                |
|:------|:------------------------------|:---------------------------------------------------------------------------------------------------------|
| RN001 | Cadastro obrigatório           | O acesso às funcionalidades depende de cadastro confirmado por e-mail.                                  |
| RN002 | Autenticação segura            | Login requer autenticação em duas etapas (código por e-mail).                                           |
| RN003 | Email único                    | Cada usuário deve ter um e-mail único e validado.                                                       |
| RN004 | Horário exclusivo              | Monitores não podem confirmar mais de uma aula no mesmo horário.                                        |
| RN006 | Pontuação e média de reputação | Monitores e estudantes acumulam média das avaliações recebidas após as aulas, influenciando reputação.  |
| RN007 | Acesso restrito do admin       | Apenas administradores podem suspender usuários ou acessar denúncias.                                   |
| RN008 | Notificações automáticas       | O sistema envia automaticamente notificações sobre ações relevantes (aulas, avaliações, denúncias etc.) |

---

## Casos de Uso
| Id    | Nome                                                          | Requisitos | Regras de Negócio |
|:------|:--------------------------------------------------------------|:-----------|:------------------|
| CSU01 | Cadastro dos Usuários com Verificação por Email               | REQ001     | RN001, RN003      |
| CSU02 | Login com Autenticação de Dois Fatores (Código por e-mail)    | REQ002     | RN002             |
| CSU03 | Inserção/Edição de Dados Pessoais de Perfil Estudante/Monitor | REQ003     | RN003             |
| CSU04 | Gerenciar Horários Disponíveis do Monitor                     | REQ004     | RN004             |
| CSU05 | Gerenciar/Consultar Aulas Marcadas do Estudante/Monitor       | REQ005     | RN004             |
| CSU06 | Busca de Monitores com Filtros pelo Estudante                 | REQ006     | RN006             |
| CSU07 | Redefinição de Senha com Email                                | REQ007     | RN003             |
| CSU08 | Solicitação de Aula pelo Estudante                            | REQ008     | RN004             |
| CSU09 | Confirmação de Aula pelo Monitor                              | REQ009     | RN004             |
| CSU10 | Avaliar Monitor/Estudante após a aula                         | REQ010     | RN006             |
| CSU11 | Gerenciamento de Usuários pelo Administrador (Suspender)      | REQ011     | RN007             |
| CSU12 | Acesso às Denúncias pelo Administrador                        | REQ012     | RN007             |
| CSU13 | Consultar Histórico de Aulas do Estudante/Monitor             | REQ013     | —                 |
| CSU14 | Ver Perfil de Outros Usuários                                 | REQ014     | RN006             |
| CSU15 | Enviar Denúncia                                               | REQ015     | RN007             |
| CSU16 | Receber Notificações                                          | REQ016     | RN008             |
| CSU17 | Busca de Usuários com Filtros pelo Administrador              | REQ017     | RN007             |

---

## Planejamento
| Sprint | Caso de Uso  | Desenvolvedor  |
|:-------|:-------------|:---------------|
| 1      | CSU01        | 1              |
| 1      | CSU04        | 2              |
| 1      | -            | 3              |
| 1      | CSU08, CSU09 | 4              |
| 1      | CSU10        | 5              |
| 2      | CSU02, CSU07 | 1              |
| 2      | CSU16        | 2              |
| 2      | -            | 3              |
| 2      | CSU06        | 4              |
| 2      | CSU15        | 5              |
| 3      | CSU03        | 1              |
| 3      | CSU12        | 2              |
| 3      | -            | 3              |
| 3      | CSU05        | 4              |
| 3      | CSU11        | 5              |
