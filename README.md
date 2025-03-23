# lennurakendus

## Rakenduse käivitamine
* Clone'i omale käesolev GitHubi repo
* ava kaust terminalis / mõnes muus käsurea rakenduses
* navigeeri back-endi kausta, milleks on flightapp  - cd flightapp
* jooksuta koodi nii - ./mvnw spring-boot:run
* kui vaja, lae alla vajaminevad maven või muud dependencyd
* rakenduse backend avaneb aadressil [localhost:8080 ](http://localhost:8080/)
* ava nüüd uus terminali aken ning navigeeri clone'itud repo kausta ning navigeeri front-end kausta - cd front-end
* lae alla vajalikud dependencyd - npm install
* ning jooksuta frontend - npm run dev
* rakenduse frontend avaneb aadressil [localhost:5173 ](http://localhost:5173/)
* kui oled rakenduse tööle saanud, saad lisada rakenduse esilehel vajutades nupule "Lisa lende" endale sobivad lennud ning testida rakendust!

### Lahenduse käik:
Rakendus on loodud Spring-Boot backendi ning Vue.js front-endiga.
Kogu protsessi jaotasin mitmeks osaks, seda näitavad ka git-i logid.
* Tegin back-endis võimalikuks lendude loomise (lisamise) ning lendude kättesaamise, et kõik ikkagi sobivalt töötaks ning ei peaks hiljem enam lisamuudatusi tegema.
* Lendude lisamisel tekitatakse suvalised hõivatud istmed kohe ning salvestatakse juba siis kui uut lendu alles luuakse
* Lisasin back-endi istmete muutmise (et broneerida istmeid frontendist)
* Lisasin valmis back-endile külge front-endi
* Lisasin vajalikud filtrid + istmete valikuks elemendid ning proovisin back-endi võimalikult vähe muuta.


Kuna mul ei olnud liiga suurt varasemat kokkupuudet front-endiga, kasutasin varasemalt natukene tuttavamat Vue.js-i. Siinkohal mainin, et enamus Vue.js failide põhjadest on loodud ChatGPT abil ning ajapuduuse tõttu ka SeatMap.vuue failis applySeatRecommendation loogika on ChatGPT loodud, kuid minu juhistega. Seda mitmel põhjusel: Ma ei tundnud ennast piisavalt kindlalt, et täies mahus ise faile valmis teha ilma vigadeta. Teiseks, aja kokkuhoiu mõttes oli hea anda ette elemendid mida vajan ning saada kätte juba sobiv template fail. Ning kolmandaks, oli lihtsam ühtse formaadiga failidele lisandusi saada kui küsida juba tuttava faili kohta.
Siiski, ei ole ükski front-endi osa otse algne genereeritud ChatGPT kood ning pidin kõiki elemente / lehekülgi mitmel viisil töötlema ise või juhiseid andes, et saada soovitud lahendus.
Back-endi osas ei kasutanud ChatGPT-lt saadud koodijuppe, kuna leidsin, et lihtsam on ise lahenduseni jõuda.
Lisaks ChatGPT-le, kasutasin ka kahte Youtube'i tutorialit: Front-endi jaoks: Coding With Mr.M - "Vue.js Creating a Full-Stack Site" koolitus ning back-endi jaoks: Telusko - "Spring Boot Project for Beginners". Kumbki tutorial ei andnud mingit kindlat koodi, vaid lihtsalt õpetasid kuidas projekte teha. Ausalt öeldes soovitaksin ise Telusko videot ka teistele, et paremat arusaama Spring-Bootist saada.

### Projektile kulunud aeg ning raskemad kohad
Ajaliselt kulutasin mitmes osas umbes 8h + 4h + 12h = 24h
Esimeseks raskemaks kohaks oli Spring Boot projekti algne loomine, et kõik vajalikud dependencied saaksid samuti lisatud, selle lahendamiseks vaatasin Telusko juhendit, tegin kaasa kogu projekti ning hiljem ei olnud see enam midagi nii keerulist.
Teiseks raskemaks kohaks oli peale back-endi valmis saamist front-endiga alustamine, kuna polnud varem kunagi ise sellist projekti teinud. Selleks aitas mind väga ChatGPT, Coding With Mr.M tutorial ning mitmed stack-overflow foorumid, kuid kood on pärit ChatGPT-lt.
Kolmandaks raskeks kohaks oli filtrite lisamine ning kõige raskem istmete filtrite rakendamise ning parimate kohtade soovitamise algoritmi välja mõtlemine. Vajalikud asjad, mida arvesse võtta andsin ChatGPT-le ning siis töötlesin antud koodi väga põhjalikult, andes aina täpsustavaid juhiseid. Arvan, et see osa võis kõige kauem võtta proportsionaalselt oma kasulikkusest. Kuigi, siinkohal aitas, et olin varem back-endi luues juba mõelnud istmete parameetrite peale ning sellest tulenevalt oli lihtne sama struktuuri peale ehitada ka front-end. ChatGPT-d juhendasin näiteks nii: "Algselt arvesta valitud kriteeriume, siis võta arvesse kas on vaja kohti kokku panna. Kokku pandavaid istekohti hakka lugema 3kaupa ning kui istekohti on rohkem kui 3, grupeeri need ühte. Kui vabasid kohti on vähem kui reas kohti, otsi kas on sobivamat või vähenda järjest olevate kohtade arvu. jne". 

### Elemendid, mida võiks tulevikus edasi arendada
* Back-endi on lisatud ResponseEntity - ei jõudndud lisada paremat error-handleimist igale elemendile, kuigi algselt plaan oli
* Rakendusele võiks kindlasti lisada testid
* Front-endis peaks lisama, et ei saa rohkem pileteid valida kui vabu kohti on (broneerib siiski ainult olemasolevad, kuid piirangu peaks siiski peale panema).
* Front-endis, kuupäevade ja aegade filtreerimine - lisada piirangud, et kohalejõudmise kp ja aeg ei saa olla enne lahkumise kp ja aega.
* Front-endi on lisatud ka lendude lisamise element testimise jaoks + uute elementide lisamiseks, selle võiks eemaldada tulevikus / lisada admin konto
* Lisada kontod, et näeksid oma broneeringuid
