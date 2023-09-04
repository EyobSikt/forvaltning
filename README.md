The Norwegian State Administration Database (NSAD)
==================================================

Kildekode til webapper for (den norske) Forvaltningsdatabasen.

Innhold
-------
Under katalogen src ligger java-kode.
Under katalogen lib ligger java-bibliotek som trengs for å kompilere java-koden.
Under katalogen webapp ligger webapplikasjoner som er laget til polsys. Disse to er:

- polsys
- polsysadmin

Webappen polsys er den offentlige webappen som genererer nettsider (og noen xml-sider) som inneholder data fra polsys.
Webappen polsysadmin er en intern webapplikasjon som brukes til å administrere databasen.



Oppdatere kode
--------------
Java-koden ligger som sagt under src-katalogen og en vil trenge bibliotekene under lib for å kompilere.

Vil en oppdatere webappene, må en oppdatere JSP-filene som ligger under WEB-INF/jsp og WEB-INF/jspf under de to webappene.
Hvis en i tillegg har oppdatert java-kode, må:
- java-koden kompileres og
- enten må class-fil-hierarkiet kopieres til under /WEB-INF/classes
- eller må det lages en jar-fil av java-koden og denne må kopieres til under /WEB-INF/lib (det er dette som gjøres nå.)


Publisere webapp
----------------
Katalogstrukturen under de to webappene, polsys og polsysadmin, følger JSP-spesifikasjonen, slik at en kan:
- kopiere katalogen over i servlet containeren (Tomcat)

Begge webappene publiseres på denne måten, men polsysadmin er en intern applikasjon om legges på en server som bare er
tilgjengelig internt.



Intern polsys-webapp uten caching
---------------------------------
Webappen polsys bruker caching. Vi trenger ofte å se dataene slik de er live i basen uten caching.
Vi har derfor kjørende en intern versjon av polsys som ikke bruker caching, denne lages slik.

Dette er altså webappen, polsys, men den kjører som polsysintern, med følgende endringer:

Under filter-def:
<param-value>172800</param-value>
dette er antall sekunder sidene skal caches og endres til 0 sek. slik:
<param-value>0</param-value>


Under Servlet-def:
<param-value>48</param-value>
dette er antall timer data skal caches i minnet i applikasjonen og endres til 0 timer slik:
<param-value>0</param-value>

