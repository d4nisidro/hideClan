# Hide Clan (Fabric mod – Minecraft 1.21.1)

Mod client-side que **oculta visualmente os membros da sua facção** (modelo,
armadura, itens e nametag) no jogo Factions, com atalho na tecla **K**
(configurável em Opções > Controles > Hide Clan).

## Como funciona

A maioria dos plugins de Factions (FactionsUUID, SavageFactions, FactionsX
etc.) coloca cada facção como um **time do scoreboard** do Minecraft. O mod
detecta se um jogador está no seu mesmo time e, se o Hide Clan estiver
ativado, cancela a renderização dele no seu cliente. Ninguém mais vê
diferença — é só visual, no seu lado.

> Se o servidor que você joga NÃO usa times de scoreboard para as facções,
> me avise o nome do plugin/servidor que eu adapto a lógica (por exemplo,
> lendo o placeholder/scoreboard lateral, ou nomes com prefixo de tag).

## Requisitos para compilar

- Java 21 (JDK)
- Conexão com a internet (o Gradle baixa Minecraft, Yarn mappings, Fabric
  Loader e Fabric API automaticamente)

Este pacote traz o `build.gradle`, `settings.gradle`, `gradle.properties` e
todo o código-fonte (`src/`), mas **não inclui o Gradle Wrapper binário**
(arquivo `gradle-wrapper.jar`), pois não tenho acesso à internet neste
ambiente para gerá-lo. É rápido resolver:

### Passo a passo

1. Instale o [Java 21](https://adoptium.net/) e o
   [Gradle](https://gradle.org/install/) (se ainda não tiver).
2. Extraia este zip em uma pasta, por exemplo `hideclan-mod/`.
3. Abra um terminal nessa pasta e rode:
   ```
   gradle wrapper --gradle-version 8.8
   ```
   Isso gera os arquivos `gradlew`, `gradlew.bat` e `gradle/wrapper/`.
4. Compile o mod:
   - Linux/Mac: `./gradlew build`
   - Windows: `gradlew.bat build`
5. O `.jar` pronto fica em `build/libs/hideclan-1.0.0.jar`.
6. Instale o **Fabric Loader** para 1.21.1 e a **Fabric API** (baixe em
   https://modrinth.com/mod/fabric-api), coloque ambos + o
   `hideclan-1.0.0.jar` na pasta `mods` do seu Minecraft.

### Alternativa mais simples

Se preferir não mexer com `gradle wrapper` manualmente, baixe o
[Fabric Example Mod](https://github.com/FabricMC/fabric-example-mod),
troque o conteúdo de `src/`, `build.gradle` e `gradle.properties` pelos
arquivos deste pacote, e rode `./gradlew build` normalmente (o wrapper já
vem pronto nesse template).

## Uso no jogo

- Pressione **K** para ativar/desativar. Uma mensagina aparece na action bar
  confirmando "Membros da facção OCULTOS" ou "VISÍVEIS".
- Você nunca fica invisível para si mesmo, só os outros membros da sua
  facção somem da sua tela.
- Pode trocar a tecla em Opções de Vídeo/Controles > categoria "Hide Clan".

## Aviso

Isso é um mod puramente **client-side / cosmético** (não dá vantagem tipo
esp/x-ray em jogadores inimigos, só limpa a visão de aliados). Ainda assim,
**confira as regras do seu servidor** — alguns servidores de Factions
proíbem qualquer mod que altere a renderização de entidades, mesmo sem dar
vantagem competitiva.
