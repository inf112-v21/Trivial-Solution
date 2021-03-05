#Deloppgave 1: Prosjekt og prosjektstruktur

###Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
Rollene i teamet fungerer bra, vi trenger oppdattere rollene litt da, men vi beholder en lignende roller som forrige gang.

De rollene som er de samme som forrige gang:
-Teamleader --> Steinar
-Utvilkere --> Alle
-Referatansvarlig --> Vi rulere på denne, går på rundgang eller hvis noen har lyst den dagen.

###Trenger vi andre roller? Og hva innebærer de for oss
De nye rollene vi introduserte mellom Oblig1 og Oblig2:
○ Nettverks-ansvarlig - Dusan
Vi kommer trenge en nettverksansvarlig for å sette det sammen slik at
flere kan spille på en gang. Denne vil da ha det overordnede
ansvaret for at denne implementasjonen blir implementert.

○ GUI-ansvarlig - Ilyas
Hvis vi skal implementere en fancy GUI mot slutten av prosjektet så kan
det være bra å ha en person som er ansvarlig for det også. Ilyas har hovedansvaret
for at dette blir implementert.

Personene som er ansvarlige for disse kan delegere oppgavene videre hvis de trenger hjelp

○ Testansvarlig - Sander
Vi trenger en testansvarlig for å dobbelsjekke at testene er gode og at det
ikke er noen logiske feil i koden. Dette blir viktigere desto lengre vi kommer i
prosjektet. Sander har hovedansvaret for at dette blir implementert.


Rollene vi ikke trenger:
Kundeansvarlig er ikke en rolle som vi har trengt per dags dato.
Vi har tross alt ingen kunder her, så vi så ikke på det som
nødvendig å ha en. 


###Team-messig erfaring og prosjektmetodikk
Vi synes at team-metodikken vi har valgt(Kanban) har funket veldig bra. Vi kunne ikke hatt Scrummøter hver eneste morgen
med timeplanene vår så vi er glade for at vi valgte Kanban. Dessuten så har alle i gruppen andre forpliktelser også, og 
takket være Kanban så kan de kan ta færre issues den uken. Alle velger issuene de vil jobbe med selv på møtene og der
bestemmer vi hva vi skal fokusere på. Deretter er det enkelt å lage issuene (Kanban-kortene) som vi setter på
Kanban brettet.

###Hvordan er gruppedynamikken?
Vi synes at vi har en god gruppedynamikken. Vi har snakket med hverandre i løpet av prosjektet og spesielt i møtene.
Her har vi forklart planene våre. Alle har vært enige i hvilken rettning vi skal gå, så det har vært veldig få misforståelser.

###Hvordan fungerer kommunikasjonen for dere?
Kommunikasjonen fungerer veldig bra. Å kommunisere med hverandre er viktig og det har vi gjort.
Se svaret over.


###Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres.
Vi har valgt å bruke Kanban som hoved prosjektmetodikk og det har som sagt virket bra. Det vi likte med Kanban og grunnen
til at vi valgte denne metodikken er at målet er å ikke produsere et overskudd. Vi brukte da
et Kanban board (github issues boardet) for å visualisere hvor langt vi har kommet i prosjektet
og hvilke oppgaver som måtte gjøres videre. 

Vi lagde derimot kanskje litt for mange issues og gikk litt vekk fra MVP
kravene som ble fastsatt i Olbig1. Dette gjorde vi derimot fordi vi prioriterte andre ting. Vi ville ha et
fungerende spill ferdig i så stor grad som mulig med tanke på spill-logikk før vi begynnte med frontend og nettverk.
Tross at de siste to også er MVP krav så prioriterte vi de ikke like mye i starten. Det er kanskje noe vi burde gjort.

Men takket være arbeidsmetodikken så har vi implementert mye av backend-koden.På grunn av Kanban har vi også valgt å fokusere på veldig 
konkrete issues så har det vært få misforståelsder i kommunikasjonen.

Vi har sammarbeidet på en god måte som nevnt over ved å møte på gruppetimene og våre individuelle møter. Det har også som sagt
resultert i få misforståelser som nevt over, så på dette punktet synes vi at vi har gjort det veldig bra.




###Under vurdering vil det vektlegges at alle bidrar til kodebasen.

Forskjellen i antall linjer skrevet kommer fra blant annet:
-kompleksitet i arbeidsoppgaver varierer veldig, så mange linjer reflekterer nødvendigvis ikke tiden som er brukt på
hver issue. Koden i ComponentFactory er for eksmepel mest gruntwork. Enkelte ting krever dessuten research og at man
leser seg opp på domenet ting omhandler. Koden på disse er ikke nødvendigvis like lang, men heller ommfattende.

-Dårlig bruk av git merge har resultert i at det ser ut som at
en person har skrevet kode som en annen har gjort. Dette er kommentert i commitsene.
Hvordan dette har skjedd er at to personer har jobbet med forskjelige issues i samme fil
som resulterer at en må pulle koden ned, sammtidig som den personen har skrevet egne commits. Dette resulterte i at git
gav oss en mergeconflict når vi brukte git add . (punktum = add alt). Når personen som pullet skulle commite og pushe igjen ble de commitsene som en annen personen hadde
overskrevetet slik at de så ut som at det siste personen hadde skrevet alt. Vi har skjønnt at vi må bruke branches mer effektivt
og det har vi gjort under arbeidet i for denne Obligen. Dette har Dusan hatt problemer med og han har litt mindre faktiskt
skrevne linjer enn det som står oppgitt i git hub statiskken. 

Ilyas og Samuel har færre linjer skrevet siden de har holdt på med spesifikke GUI-relaterte issues, som har krevd mer reasearch på
dette stadiet av prosjektet så derfor har de ikke commitetet like mye. Det de holder på med er å finne
ut hvordan vi kan få det til at registeret med kortene og selve spillebrettet vises i samme vindu.

Steinar har fokusert mest på backenden av proskjektet på dette tidspunktet og har dermed skrevet flere linjer. Han begynner på
GUI-delen snart. Det er et par ting igjen på Backend, men vi er fornøyde med hvor langt vi har komt her generelt nå.

Sander fokuserte også stort sett på GUI-delene av prosjektet (spesifikkt kort implementasjonen) og har brukt en stor del
av tiden på å researche dette.

Dusan har gjort en del av backend og har vært ansvarlig for at spilleren kan vinne etter å hente flaggene i det siste. Det er nå
implementert på backenden så han har begynt med å reaserche hvordan vi skal sette opp nettworkingen (Multiplayer)



Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som
havner i referat)..
Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.


#Deloppgave 2 - Krav

###Brukerhistorier, Akseptansekrav, arbeidsoppgaver
Du kan finne disse hvis du går inn på github prosjektet vårt.
Gå deretter til Projects og klick på Trivial-Solution. Dette
er prosjektbrettet vårt. Her skriver vi nå Brukerhistorier, Akseptansekrav og Arbeidsoppgaver
for hver issue. Vi skriver også tilhørende tester som blir skrevet med tanke på akseptansekravene.
Disse finner man i test packagen.

Vi har noen oppgaver som vi har startet med nå, men akseptansekrav er ikke definert.

###Prioritering fremover
Fremover så kommer vi prioritere networking(Sette opp multiplayer) og gui'en.
Vi har prioritert disse oppgavene fordi vi ligger litt bak her. På andre siden så er backenden
for hele spillet stort sett ferdig. Så nå må vi bare implementere Gui'en og Mutiplayer som mangler.

###Justeringer i MVP krav
Vi har ikke gjort noen forandringer i MVP kravene per say. Men vi har prioritert andre ting som vi syntes var viktigere. Nemlig
å få spill-logikken på plass. MVP krav 1-5 og 7-9 illustrere til en viss grad hva dette innebærer, men vi ville ha selve spill-logikken
på plass før vi begynte med resten. Da tenkte vi på komponentene i spillet som laserer, vegger, gear, wrench og andre slike
ting som en del av spillet. Tanken vår var at hvis vi gjorde det på denne måten så ville vi få en bedre oversikt over hvordan vi skulle
gå videre med å lage spillet. Dette var en god måte å bli bedre kjent med spillet på og vi tenkte at vi kunne gjøre en bedre jobb
hvis vi gjorde det på denne måten. 

MultiPlayer er også et viktig MVP-krav men vi mener at spillet og komponentene fungerer på en 
logisk måte er viktigere på dette tidspunktet. Dessuten så kan det være problematisk å implementere logikken til for eksempel en
vegg senere. Målet vårt er tross alt til syvende og sist å ha et full-fledged spill og ikke bare det som er beskrevet i MVP. Med tanke
på dette så har vi prioritert alitt annerledes.

Det skal sies at vi kanskje brukte mer tid enn det som var optimalt på å lage komponentene til brettet. Denne tiden kunne
gått til mutiplayer, men vi er glade for å ha komponentene på plass nå. De er tross alt med i brettet og dermed
en viktig faktor for spillet. På en måte så kan man kanskje si at siden vi prioriterte dette sterkere i begynnelsen
så kan vi telle det som et

###Prioriterte krav
Vi prioriterte Krav 1-3 og 7-8 under Oblig1. Vi har fortsatt med
disse nå også, samt at vi har fullført 4-5 og 9 også. 6 er det
neste som skal inn, samt GUI-fin pussing.

###Bugs i kravene
Krav 2 (Vise brikke på spillebrett): Et undermål for dette kravet
er at roboten ikke skal vises på ruten den har gått ifra. Det gjør den
nå.

Legg til flere Bugs imorgen hvis vi har noen!


#Deloppgave 3


