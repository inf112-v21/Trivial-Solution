# Oblig-3:
## Deloppgave 1

### Møtereferater finnes i mappen Møte-referat.

### Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
- Ganske fint, vi beholder de vi har valgt.
- Altså Steinar er teamleader, Sander er testansvarlig, Ilyas er GUI-ansvarlig og Dusan er nettverksansvarlig.

### Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? 
- Å ha alt fint og oversiktlig på project-boardet er veldig nyttig. Det gjør det enkelt å samarbeide, og vite hva som skal bli gjort.
- Vi har nevnt dette tidligere men Kanban som prosjektmetodikk har fungert bra for oss. Den er fleksibel og dekker våre behov, såt vi kommer fortsette å bruke den.

### Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?
- Teamet synes denne prosjektmetodikken har fungert veldig bra. Det er verdt å nevne at vår prosjektmetodikk følger en strukturet plan som finnes på issues-siden.  
	
### Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om feilretting, men om hvordan man jobber og kommuniserer.   
- Vi er så og si ferdig med å implementere alt av spillogikk.
- GUI og multiplayer henger litt bak, men det er det vi fokusere på nå så vi tror ikke det skal være noen problem å bli ferdige med de Vi har satt alle resursene over på disse punktene. Dusan fokuserer 100% på mulitplayer nå for eksempel.m.

### Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.
- Prøve å starte på en ny issue med én gang vi er ferdig med den gamle.
- Hvis noen sitter fast på en oppgave i flere uker, la heller noen andre ta over med friske øyne.

### Forklar kort hvordan dere har prioritert oppgavene fremover. Legg ved skjermdump av project board ved innlevering
- Prioritering av oppgaver følger fra brukerhistoriene 
- Vi har nå labels på issuene på Github som indikerer prioritetsnivået.
- Mesteparten av backend er ferdig, nå har vi et godt grunnlag for frontend og multiplayer
- Derfor blir fokuset generelt satt på GUI og multiplayer heretter
- Dusan har brukt tid på å researche og planlegge hvordan multiplayer skal settes opp, og nå er vi klare for å begynne selve implementasjonen.
- Vi har lagt ved et screenshot av prosjektbrettet fra 26.03.21 i Deliverables.
		
### Hvordan fungerer gruppedynamikken og kommunikasjonen?
- Sunn diskusjon om ideer på kodeløsninger og implementasjon av nye funksjonaliteter. Alle er villig til å nevne om noe må gjøres: bedre eller enklere (kan legges til fler). Vi har et slags demokrati hvor Steinar hovedsakelig legger til issues som må jobbes med og alle velger selv hvilken issue de vil jobbe med basert på prioritetsnivået.
	
## Deloppgave 2

### Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.

Noe av det vi har gjort:
- Implementert en egen screen for å lage en singleplayer-match, og en loading screen.
- Implementert samlebånd, muttere og skiftenøkler, og dermed er så og si alt av spillogikk ferdig.
- Gjort at runder blir simulert pent på skjermen, med litt tid mellom hvert trekk robotene gjør.
- Implementert custom designs av robotene, tegnet av en ekstern kontakt.
- Researchet og laget en plan for implementering av multiplayer.
- Fikset at prosjektet ikke “buildet” i Travis CI.
- Generelt refaktorert en stor del av prosjektet, nå skal det være enklere å navigere i koden.
- Vi har researchet og laget en konkret plan for hvordan vi kan implementere multiplayer, du kan gå i networking-branchen for å se hvor langt vi er kommet med det.
  
Noe av det som gjenstår:
- Vi har fått til at kort blir vist og valgt på skjermen, men det ser ikke særlig pent ut. Vi skal rydde i det senere.
- Vi må også få til at antall liv og hp vises på skjermen.
- Alt innen multiplayer gjenstår. Implementeringen skal begynne her. (se Networking-branchen for å se hvor langt vi har kommet på dette)
- Fjerne eventuelle bugs.

### Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også.
- Prosjektboardet på Github oppdateres konstant, så stikk innom der når som helst for å se hva vi jobber med/planlegger å jobbe med

### Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs).
- “Game Over”-screenen vises feil når spillet vinnes.
- Spillvinduet lukkes ikke ordentlig om man prøver å krysse det.
- Hitboksene til knappene in-game er wacky, f. eks om man prøver å klikke på "Ready" vil man bomme og treffe "Power Down" istedet.

### Kravlista er lang, men det er ikke nødvendig å levere på alle kravene hvis det ikke er realistisk. Det er viktigere at de oppgavene som er utført holder høy kvalitet. Utførte oppgaver skal være ferdige.
- Vi påpeker at om en oppgave er utført medfører det at den også er ferdig, og omvendt. Så at alle oppgaver som er utførte også skal være ferdige er en tautologi. 
		
## Deloppgave 3
### Dokumentere hvordan prosjektet bygger, testes og kjøres
- Se README-filen.

### Lever klassediagram
- Se bildet i Deliverables.

### Lever beskrivelser ved manuelt tester av hvordan testen foregår, slik at gruppeleder kan utføre testen selv.
- Vi har ingen manuelle tester, annet enn å starte opp spillet og umiddelbart sjekke om GUI ser bra ut eller ikke.
- Vi prøvde å skrive en test som bare automatisk går rett til in-game uten å gå igjennom hovedmenyen, men det var overraskende vanskelig med hvordan Libgdx funker.

### Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler)
- Forskjellen i antall commits av hver person skyldes blant annet mangelen av bruk av branches i begynnelsen av prosjektet, noe som førte til at merging gikk galt og overførte kodelinjer til feil personer.
- Skinnet som blir brukt av GUI-en var i seg selv 2500 linjer.
- Automatisk refactoring i IntelliJ ga også over 1000 linjer i en commit.
- Vi har nå prøvd å variere litt i hvem som committer arbeidet gjort under parprogrammering.
- sco008 og Samuel er samme person, vi aner ikke hvorfor halvparten av commitene hans blir på det andre navnet.
