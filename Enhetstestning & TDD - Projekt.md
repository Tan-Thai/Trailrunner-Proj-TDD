[Introduktion](#Introduktion)
[Bakgrund](#Bakgrund)
[Godkänt-del](#Godkänt-del)
	[Förklaring G](#Förklaring-G)
	[Kriterier G](#Kriterier-G)
	[Versionshantering](#Versionshantering)
[VG-del](#VG-del)
	[Förklaring VG](#Förklaring-VG)
	[Bakgrund-VG](#Bakgrund-VG)
	[API](#API)
	[Kriterier-VG](#Kriterier-VG)
[Inlämning](#Inlämning)
	[Projekt](#Projekt)
	[TDD-Dagbok](#TDD-Dagbok)
	[Tips & Trix](#Tips&Trix)	

# Introduktion

I det här projektet kommer vi öva på att utveckla med ramverket Testdriven Utveckling (Test-Driven Development, TDD). Projektet går ut på att ta fram en enkel applikation, TrailRunner™, som hanterar löprundor för en användare.   

Godkänt-delen av labben är tänkt att göras i par. Väl Godkänt- delen skall göras **enskilt**. Projektet skall lämnas in innehållandes implementationskod **och** testkod, samt en coverage-rapport. Utöver det skall en TDD-dagbok lämnas in där ni beskriver utvecklingsarbetet steg för steg.


# Bakgrund

TDD är en fundamental del av modern mjukvaruutveckling och testning där vi: 
1. Först skriver ett test som beskriver önskad funktionalitet 
2. Ser testet misslyckas (RED) 
3. Implementerar den enklaste koden som får testet att lyckas (GREEN) 
4. Förbättrar koden utan att ändra dess beteende (REFACTOR) 

Denna metodik ger flera fördelar: 
- Säkerställer att all kod har tester från början 
- Resulterar i mer modulär kod som blir enklare att underhålla 
- Minskar tiden som spenderas på debugging
- Fungerar som levande dokumentation av kodens beteende


# Godkänt-del

Följande sektion förklarar mål, krav och kriterier för att uppnå betyget **Godkänt**. 
Notera att beskrivningar som går under "Avancerat" **inte** är obligatoriska. Dessa kan ses som en "bonusutmaning"/ett sätt för ett att bli bättre på programmering! 

## Förklaring-G

TrailRunner skall utvecklas med hjälp av TDD och JUnit. Utöver det behöver programmet även uppfylla de krav och specifikationer som defnieras i mer detalj under [Kriterier-G](#Kriterier-G). Användaren ska kunna spara en löprunda med attribut så som distans, tid och datum. I Detta liknar applikationer som Strava, RunKeeper etc, men väldigt förenklade.

## Kriterier-G

**För att uppnå betyget godkänt behöver samtliga kriterier uppfyllas.** 

**I.** Användare skall kunna spara en löprunda med följande attribut:
- Distans (km)
- Tid (timmar, minuter, sekunder)
- Datum (År-Månad-Dag)
- Om inget datum anges skall dagens datum användas.
Avancerat: 
- Om Distans eller Tid saknas skall applikationen kasta ett Exception. 

**II.** Varje löprunda skall skapas med ett identifikationsnummer (String)
-   Två löprundor får inte ha samma identifikationsnummer.
Avancerat: 
- Identifikationsnumret tilldelas automatiskt. 

**III.**  Varje löprunda skall automatiskt beräkna:
- Medelhastighet enligt formeln: $$ hastighet = {distans(km) \over tid(hrs)} $$
- Kilometertid enligt formeln: $$ kilometertid = {tid(min) \over distans(km)} $$
- Användaren skall kunna ställa in sin **längd (cm)**, **vikt (kg)** och **ålder (år)**.
- Användaren får ett Fitness Score efter varje löptur, detta kalkyleras enligt följande formel, Fitness Score ska inte kunna understiga 0 och ska bara visas som **heltal**: $$ Fitness Score = CS + (d + {v \over p}) - {t \over 2} $$
_där:_
$$ CS = \textit{Current Score *}$$
$$d = distans (km)$$
$$v = medelhastighet (km/h)$$
$$p = kilometertid (min/km)$$
$$t = \textit{Dagar sedan senaste löpturen *}$$
`* Om det är första löpturen är värdet 0`

**IV.** Det skall gå att beräkna:
- Totala distansen för ens sparade löprundor
- Medeldistansen för ens sparade löprundor
- Printa ut detaljerna för en löprunda genom att ange ett korrekt identifikationsnummer
- Radera en löprunda genom att ange dess identifikationsnummer
Avancerat: 
- Om ett felaktigt identifikationsnummer anges skall ett felmeddelande skrivas ut.
- Det skall gå att filtrera löprundor med avseende på distans och/eller tid.

**V.** Kodtäckning och Testning: 
- Coverage ska vara så högt som möjligt (ej krav på exakt 100%)
- En JaCoCo-rapport ska genereras och inkluderas i inlämningen
- Tester ska vara välstrukturerade och följa god testpraxis: 
	- Ett test testar en sak 
	- Enskilda klasser för testklasser
	- Beskrivande testnamn


För Godkänt betyg skall ovan utvecklas enligt principerna för test-driven utveckling TDD (avancerade krav är inte nödvändiga), testerna skall var gröna och gå igenom med en kodtäckning på så nära 100% som möjligt. Kodtäckning skall beräknas med JaCoCo. 

**Detta skall göras i par enligt principerna för parprogrammering. En utvecklar, den andra observererar och kommer med idéer. Byt roller med jämna mellanrum (ex var 30 minut).**


## Versionshantering

Som en del av utvecklingsprocessen ska koden versionshanteras med Git. Github är att rekommendera här, om ni inte arbetat med branches tidigare rekommenderar jag starkt att ni gör det nu. 

*Exempel på användning*: 
- Branchstruktur:
	- **main**: Innehåller stabil, färdig kod
	- **refactor**: Innehåller kod som refaktoreras
	- **feature**: Innehåller kod som implementerar ny funktionalitet
- Användning: 
	- Gör en commit för varje TDD-cykel (Red-Green-Refactor)
	- När alla tester är gröna kan dom merge:as till main 


# VG-Del
Beskriver kriterier för att uppnå betyget VG. 

## Förklaring-VG

I denna del vidareutvecklar vi **TrailRunner™** till att använda externt (mockat) lagringssystem, men ALL övrig funktionalitet från Godkänt-delen skall finnas kvar. För Väl Godkänt skall du använda  ett interface mot ett externt lagringssystem (`FileStorage.java`). Utmaningen är att "mocka" att vi lagrar löprundorna i en fil istället för att lagra dessa lokalt i minnet.  

**Detta skall göras enskilt.** 

## Bakgrund-VG

I denna del kommer ni använda Mockito för att "mocka" fillagring. Detta simulerar en verklig situation där ni behöver testa kod som interagerar med externa system eller komponenter som ännu inte är färdigutvecklade. Denna typ av mocking är vanlig i industrin när man till exempel testar: 
- Databaskopplingar 
- API-anrop 
- Filsystemoperationer 
- Nätverkskommunikation

Mocking är en central del av denna uppgift eftersom: 
1. Det låter oss testa kod som interagerar med externa system (i detta fall filsystem) utan att faktiskt skriva till disk 
2. Vi kan simulera olika scenarion som är svåra att återskapa med ett riktigt filsystem (t.ex. diskfel) 
3. Det gör våra tester snabbare och mer pålitliga eftersom de inte är beroende av externa resurser 

## API

Följande API finns i projektet som ni kan ladda ner. Notera att ni alltså inte behöver skapa kod som implementerar funktionalitet för att spara ner datan till en fil, API:n ska använda mocking. 

```java
public void createRecord(String id, double distance, int time_seconds, LocalDate date) throws IOException {}

public Record readRecord(String id) throws IOException {}

public List<String> getRecordIDs() {}

public void deleteRecord(String id) throws IOException {}
```


## Kriterier-VG

Alla tidigare funktioner i applikationen skall nu utgå från att den sparas i en fil istället för arbetsminnet (detta behöver inte implementeras, det skall istället mockas). 
Krav:  
- En ny löprunda skall läggas till automatiskt i filen.
- Detaljerna för en löprunda skall nu läsas av via filen.
- Det skall gå att läsa av detaljerna, samt ta bort en löprunda genom att ange dess ID.
- Det skall gå att filtrera löprundor baserat på deras distans.
- Om ingen match finns skall ett meddelande skrivas ut.

För Väl Godkänt  betyg ovan utvecklas enligt principerna för test-driven utveckling TDD, testerna skall var gröna och gå igenom med en kodtäckning på så nära 100% som möjligt. Kodtäckning skall beräknas med JaCoCo. 

**Detta skall göras enskilt.**


# Inlämning

Beskriver krav på inlämningen. 
## Projekt

Projektet lämnas in som ett komplett Maven-projekt i en zip-fil **eller** som en länk till ett Github-repo (se till att jag har access, antingen via en inbjudan eller att ditt repo är publikt).

## TDD-Dagbok

Lämnas in ihop med resten av projektet (samma zip-fil eller repo) som **PDF** eller **Markdown (.md)**. Det krävs ingen dokumentation av projektet utöver TDD-Dagboken. Varje "inlägg" i dagboken ska vara markerad med datum och innehålla en **kortfattad** förklaring av hur utvecklingsprocessen fortskrider och resonemang kring hur du arbetar med TDD. Kan även vara på listform.

**Exempel på dagboksinlägg**: 
Datum: 1/1/2025
1. Krav: Två löprundor får inte ha samma ID.  
   Test: Man kan inte skapa en löprunda med ett existerande ID.  
   Reflektion: Insåg att jag måste utöka testet till för att kolla så att rätt felmeddelande skrivs ut om man försöker skapa en löprunda med identiskt ID.
2. Utökar testet från (1) med test som kollar felmeddelandet vid existerande ID.
3. ...

**TDD-dagboken är viktigare än implementationskoden!**


## Tips&Trix

- Välj ut ett requirement först och formulera ett test utefter det. Försök välja ett test som känns relativt enkelt att skriva
- Det är enklast att börja med användaren och dennes attributvariabler
- Om du sparar användarens löprundor i en Map kan Java automatiskt se till att alla rundor har unika ID. Se upp bara för vad som händer om du försöker spara en löprunda med ett ID som redan existerar!
- Det är rimligt att ha separata klasser för (åtminstone) användare och rundor. Skriv tester för de två klasserna i separata testklasser.
- Det viktigaste med projektet är att ni jobbar i enlighet med TDD, och att ni **dokumenterar** vad ni gör. **TDD-dagboken är viktigare än implementationskoden!** Om ni fastnar vid någon implementation, eller inte vet hur ni skall gå vidare, börja formulera test från ett annat håll
- Gör en ToDo-list, det blir enklare för er och ni kan använda det för era TDD-dagböcker
- Ta hjälp av, och fråga varandra om tips!





