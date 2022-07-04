# Laikino apgyvendinimo paslaugų rezervavimo sistema
 Agentais grįsta sistema, kuri naudotojui leidžia surasti pigiausią būdą apsygiventi nurodytame mieste ir rezervervuoti apgyvendinimo paslaugas. Sistema ieško pačio pigiausio varianto likti nurodytame mieste, jeigu yra apgyvendinimo vietų nurodytame mieste. Gautas apgyvendinimo planas gali susidėti kelių apgyvendinimo vietų, jeigu pigiausia apgyvendinimo paslauga yra rezervuota dalį apgyvendinimo laikotarpio.
 
 ![image](https://user-images.githubusercontent.com/108531874/177113576-a5a78c64-8ff2-4eef-8319-ba04097edfe3.png)


### Kad projektas susikompiliuotu, reikia įtraukti bibliotekas iš "libraries" aplanko į IDE ir projektą.

Rezervavimo paslaugų duomenys ir rezervavimo datos saugomos "duomenysX.txt" failuose.


Sistemą sudarantys agentai:

Buyer agentas: kontroliuoja naudotojo sąsają. Naudotojas gali nurodyti miestą, šąlį, žmonių skaičių ir apsistojimo laiką, 
tada atlikti apgyvendinimo vietų paiešką. Agentas kreipsis į kitus agentus, kurie teikia apgyvendinimo paslaugų sąrašo pateikimo 
paslaugas, ir iš gautų rezultatų sudeda pigiausią apgyvendinimo planą su nurodytomis apgyvendinimo datomis. Naudotojui pateikia 
pigiausią apgyvendinimo variantą ir leidžia naudotojui rezervuoti pateiktas apgyvendinimo vietas. Agentas bandant rezervuoti 
apgyvendinimo vietą siunčia užklausą agentui, kuris pateikė šią vietą, bandant ją rezervuoti.


Seller agentas: saugo apgyvendinimo paslaugų sąrašą su rezervavimo datomis ir kainomis. Teikia apgyvendinimo vietų sąrašo
pateikimo ir apgyvendinimo vietų rezervavimo paslaugas. Gavęs rezervavimo užklausą agentas atsiunčia patvirtinimo pranešimą,
ar pavyko rezervuoti.
