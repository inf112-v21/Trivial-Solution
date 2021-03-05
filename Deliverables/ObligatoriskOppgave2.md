#Deloppgave 1: Prosjekt og prosjektstruktur

###Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
Rollene i teamet fungerer bra, vi trenger oppdatere rollene litt da, men vi beholder en lignende roller som forrige gang.

De rollene som er de samme som forrige gang:
-Teamleader --> Steinar
-Utvilkere --> Alle
-Referatansvarlig --> Vi rulerer på denne, går på rundgang eller hvis noen har lyst den dagen.

###Trenger vi andre roller? Og hva innebærer de for oss
De nye rollene vi introduserte mellom Oblig1 og Oblig2:
○ Nettverks-ansvarlig - Dusan
Vi kommer trenge en nettverksansvarlig for å sette det sammen slik at
flere kan spille på en gang. Denne vil da ha det overordnede
ansvaret for at denne implementasjonen blir implementert.

○ GUI-ansvarlig - Ilyas
Det kan være bra å ha en person som er ansvarlig for GUI også. Ilyas har hovedansvaret
for at dette blir implementert.

Personene som er ansvarlige for disse kan delegere oppgavene videre hvis de trenger hjelp

○ Testansvarlig - Sander
Vi trenger en testansvarlig for å dobbelsjekke at testene er gode og at det
ikke er noen logiske feil i koden. Dette blir viktigere desto lengre vi kommer i
prosjektet. Sander har hovedansvaret for at dette blir implementert.


Rollene vi ikke trenger:
Kundeansvarlig er ikke en rolle som vi har trengt per dags dato.
Vi har tross alt ingen kunder, så vi så ikke på det som
nødvendig å ha en kundeansvarlig. Det kan være nødvendig å utnevne en person som skal være kundeansvarling
for presentasjonen neste uke da, men vi har ikke sett poenget med denne rollen inntil videre.


###Team-messig erfaring og prosjektmetodikk
Vi synes at team-metodikken vi har valgt(Kanban) har funket veldig bra. Vi kunne ikke hatt Scrummøter hver eneste morgen
med timeplanene våre så vi er glade for at vi valgte Kanban. Dessuten så har alle i gruppen andre forpliktelser også, og 
takket være Kanban så kan de kan ta færre issues den uken. Alle velger issuene de vil jobbe med selv på møtene og der
bestemmer vi hva vi skal fokusere på. Deretter er det enkelt å lage issuene (Kanban-kortene) som vi setter på
Kanban brettet (Prosjektboardet i github).

Vi har også brukt litt pair programming(altså XP). Vi har derimot ikke brukt noe Scrum siden Oblig1. På grunn av
årsakene nevnt over har vi tatt avstand fra Scrum.

###Hvordan er gruppedynamikken?
Vi synes at vi har en god gruppedynamikk. Vi har snakket med hverandre i løpet av prosjektet og spesielt i møtene.
Her har vi forklart planene våre. Alle har vært enige i hvilken retning vi skal gå, så det har vært veldig få misforståelser.


###Hvordan fungerer kommunikasjonen for dere?
Kommunikasjonen fungerer veldig bra. Å kommunisere med hverandre er viktig og det har vi gjort, ellers ville vi
ikke kommet noen vei. Ellers så er tonen god, alle meninger blir hørt. Se svaret over.


###Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres.
Vi har valgt å bruke Kanban som hoved prosjektmetodikk og det har som sagt virket bra. Det vi likte med Kanban og grunnen
til at vi valgte denne metodikken er at målet er å ikke produsere et overskudd. Vi brukte da
et Kanban board (github issues boardet) for å visualisere hvor langt vi har kommet i prosjektet
og hvilke oppgaver som måtte gjøres videre. 

Vi lagde derimot kanskje litt for mange issues og gikk litt bort fra MVP
kravene som ble fastsatt i Oblig1. Dette gjorde vi derimot fordi vi prioriterte andre ting. Vi ville ha et
fungerende spill ferdig i så stor grad som mulig med tanke på spill-logikk, før vi begynte med frontend og nettverk.
Til tross at de siste to også er MVP krav så prioriterte vi de ikke like mye i starten. Det er kanskje noe vi burde ha gjort.
Denne metoden vi har jobbet på hadde mest sannsynlig virket bedre hvis det kun var 1 innlevering i faget, men siden
det er obliger annenhver uke så har vi jobbet med feil krav. Samtidig så er hovedmålet å ha et godt
fungerende spill (som vi har jobbet mot) så vi kommer hente oss igjen uten problemer.

Dermed har vi implementert mye av backend-koden (spill-logikken). Vi har derimot ikke implementert
mye GUI og har dermed ikke en måte å vise det 100% enda (noe av GUI er implementert). 

På grunn av Kanban har vi også valgt å fokusere på veldig 
konkrete issues, så det har vært få misforståelsder i kommunikasjonen (som nevnt over).

Vi har samarbeidet på en god måte som nevnt over ved å møte på gruppetimene og våre individuelle møter.



###Under vurdering vil det vektlegges at alle bidrar til kodebasen.

Forskjellen i antall linjer skrevet kommer fra blant annet:
- Kompleksitet i arbeidsoppgaver varierer veldig, så mange linjer reflekterer nødvendigvis ikke tiden som er brukt på
hver issue. Koden i ComponentFactory er for det meste gruntwork. Enkelte ting krever dessuten research og at man
leser seg opp på domenet ting omhandler. Koden på disse er ikke nødvendigvis like lang, men heller omfattende. Dette
er noe av grunnen for at Samuel og Ilyas ligger bak i antall commits/ linjer skrevet.

- Dårlig bruk av git merge har resultert i at det ser ut som at
en person har skrevet kode som en annen har gjort. Dette er kommentert i commitene.
Hvordan dette har skjedd er at to personer har jobbet med forskjelige issues i samme fil
som resulterer at en må pulle koden ned, sammtidig som den personen har skrevet egne commits. Dette resulterte i at git
gav oss en mergeconflict når vi brukte git add . (punktum = add alt). Når personen som pullet skulle commite og pushe igjen ble de commitene som en annen person hadde
overskrevet slik at de så ut som at den siste personen hadde skrevet alt. Vi har skjønt at vi må bruke branches mer effektivt
og det har vi gjort under arbeidet i denne Obligen. Dette har Dusan hatt problemer med og han har litt mindre faktiske
skrevne linjer enn det som står oppgitt i github statistikken. 

Ilyas og Samuel har færre linjer skrevet siden de har holdt på med spesifikke GUI-relaterte issues, som har krevd mer reasearch på
dette stadiet av prosjektet, så derfor har de ikke commitet like mye. Det de holder på med er å finne
ut hvordan vi kan få det til at registeret med kortene og selve spillebrettet vises i samme vindu.

Steinar har fokusert mest på backenden av proskjektet på dette tidspunktet og har dermed skrevet flere linjer. Han begynner på
GUI-delen snart. Det er et par ting igjen på Backend, men vi er fornøyde med hvor langt vi har kommet her generelt nå.

Sander fokuserte også stort sett på GUI-delene av prosjektet (spesifikt kort implementasjonen) og har brukt en stor del
av tiden på å researche dette.

Dusan har gjort en del av backend og har vært ansvarlig for at spilleren kan vinne etter å hente flaggene i det siste. Det er nå
implementert i backenden, så han har begynt på å reaserche hvordan vi skal sette opp networkingen (Multiplayer)



###Referat fra møter
For å se møtereferatene se under mappen Møte-referat. For å se møtene etter Oblig 1 se alle møtene med datoer etter
12 Februar.

### Forbedringspunkter
1. Bruke branches mer (Nå når vi har et spillbart spill så er det viktigere å bruke branches for å unngå å
   ødelegge noe midlertidlig.)
2. Skrive flere tester (Dette har vi begynnt med allerede da)
3. Vi har folk som er ansvarlige for sine spesifikke punkt. Akkurat nå så står disse oppsatt med issues som har et stort
   arbeidskrav. Vi må ikke være redd for å delegere små issues videre.


#Deloppgave 2 - Krav

###Brukerhistorier, Akseptansekrav, arbeidsoppgaver
Du kan finne disse hvis du går inn på github prosjektet vårt.
Gå deretter til Projects og klikk på Trivial-Solution. Dette
er prosjektbrettet vårt. Her skriver vi nå Brukerhistorier, Akseptansekrav og Arbeidsoppgaver
for hver issue. Vi skriver også tilhørende tester som blir skrevet med tanke på akseptansekravene.
Disse finner man i test packagen.

###Prioritering fremover
Fremover så kommer vi prioritere GUI først og fremst. Målet er å få til at Singleplayer virker 
før vi begynner på Multiplayer. Vi (Dusan) kommer fortsette å researche hvordan
dette skal settes opp, men gruppen sin hovedprioritering blir å implementere GUI.
Vi har prioritert disse oppgavene fordi vi ligger litt bak der. På andre siden så er backenden
for hele spillet stort sett ferdig. 

###Justeringer i MVP krav
Vi har gjort 1 stor endring i MVP kravene som er at vi ikke har implementert Multiplayer enda. 

Vi har derimot prioritert andre ting som vi syntes var viktigere, nemlig
å få spill-logikken på plass. MVP krav 1-5 og 7-9 illustrerer til en viss grad hva dette innebærer, men vi ville ha selve spill-logikken
på plass før vi begynte med resten. Da tenkte vi på komponentene i spillet som lasere, vegger, gear, wrench og andre slike
ting som en del av spillet. Tanken vår var at hvis vi gjorde det på denne måten så ville vi få en bedre oversikt over hvordan vi skulle
gå videre med å lage spillet. Dette var en god måte å bli bedre kjent med spillet på og vi tenkte at vi kunne gjøre en bedre jobb
hvis vi gjorde det på denne måten. 

Multiplayer er også et viktig MVP-krav men vi mener at måten spillet og komponentene fungerer på er viktigere på dette tidspunktet. 
Dessuten så kan det være problematisk å implementere logikken til for eksempel en vegg senere. 
Målet vårt er tross alt å ha et fully-fledged spill og ikke bare det som er beskrevet i MVP. Med tanke
på dette så har vi prioritert litt annerledes.


###Prioriterte krav
Vi prioriterte som nevnt backend. GUI har vi stort sett ikke sett på før de siste to ukene, 
og det har dessverre tatt mye lengre tid enn forventet. Fremover kommer vi til å fokusere på GUIen.

###Bugs i kravene
Noen ganger flytter roboten seg litt rart, vi vet ikke helt hvorfor ennå.


#Deloppgave 3
Se README-filen for teknisk oppsett, og spillguide.