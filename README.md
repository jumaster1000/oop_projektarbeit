# Leitstellensystem - Projektarbeit OOP
### Projektarbeit Objektorientiertes Programmieren | 1. Semester - Digital Enterprise Management | Cosmo Stahn, Julian Schnabl

>**Kurzbeschreibung:**
>Bei dem Programm handelt es sich um ein Leitstellensystem (LSS) welches generell in Integrierten Leitstellen (ILS) der Feuerwehren und des Rettungsdienstes eingesetzt wird. Für diese Projektarbeit wurde das System im Vergleich zu einem reellen LSS vereinfacht.

### Gliederung
1. Hintergrund (JU)
2. Einführung GUI-Fenster // GUI Gesamt als Bild und Kurz Sinn erläutern (JU)
3. Erklärung einzelne Bereiche des GUI-Fensters (JU)
4. Projektstruktur (CO)
5. Funktionalitäten (CO) 
6. JUnit Test (JU)
7. Ausführen des Programms (JU)
8. Autoren (JU)

### Hintergrund:
Über eine ILS wird die europaweite Notrufnummer 112 entgegengenommen. Welche Leitstelle damit erreicht wird, entscheidet sich durch die Mobilfunkmasten in denen das Handy eingewählt ist oder durch die Adresse bei der ein Festnetzanschluss registriert ist. Die verschiedenen Leitstellen werden nach Kommunen und den Landkreisen aufgeteilt. In Ulm ist die ILS in die Hauptfeuerwache der Feuerwehr Ulm in der Keplerstraße 38 in der Oststadt integriert. Diese ILS ist für das Stadtgebiet Ulm, sowie für den Alb-Donau Kreis zuständig. Für Neu-Ulm ist die ILS Donau-Iller zuständig, welche in Krumbach lokalisiert ist. Sollte man, z.B. weil man direkt an der Donau auf der Neu-Ulmer Seite steht in der ILS in Ulm rauskommen, wird der Anruf von dem Leitstellendisponenten direkt in die ILS Donau-Iller weitergeleitet. Die Notrufnummer 110 der Polizei wird nicht über die ILS sondern über das Führungs- und Lagezentrum (FLZ) des jewiligen Polizeipräsidiums bedient. 

Bei einem eingehenden Notruf wird der Anruf von einem Leitstellendisponenten entgegengenommen, dieser führt die bekannte Notrufabfrage mit den W-Fragen durch und gibt die angegeben Daten in ein LLS ein. Sobald alle grundlegenden Daten wie die Adresse und der Notfall klar sind, wählt der Disponent ein Alarmstichwort aus und nach einer festgelegten Alarm- und Ausrückeordnung (AAO) werden entsprechende Einsatzkräfte alarmiert. Oft geschiet die Alarmierung während des Telefonats und nach der Alarmierung werden weiterhin Informationen aufgenomern, diese werden weiterhin in das LSS eingegeben. Sind die Einsatzkräfte auf dem Weg, können so weitere Informationen über die Funkverbindung nachgereicht werden, ohne wertvolle Zeit zu verlieren. 


### 4. Projektstruktur:
Das Projekt besteht aus zwei zentralen Klassen. Die Klasse Einsatz dient als Bauplan für einzelne Einsätze und speichert alle einsatzrelevanten Informationen. Die Klasse Leitstellensystem fasst die grafische Benutzeroberfläche sowie die komplette Steuerungslogik des Systems zusammen und greift auf die erzeugten Einsatzobjekte zu.

#### Klasse „Leitstellensystem“
Die Klasse Leitstellensystem erbt von JFrame und stellt somit das Hauptfenster der Anwendung dar. Sie ist für die gesamte Benutzerinteraktion, die Verarbeitung der Eingaben sowie die Verwaltung der Einsätze zuständig.

#### Klasse „Einsatz“
Die Klasse Einsatz fungiert als Bauplan für einzelne Einsatzobjekte und beschreibt, welche Informationen ein Einsatz enthält.

#### Zusammenspiel der Klassen
Die Klasse Einsatz wird innerhalb des Projekts ausschließlich als Datenobjekt verwendet. Sie enthält keine Logik zur Darstellung oder Bedienung, sondern stellt lediglich strukturierte Einsatzinformationen bereit.
Die Klasse Leitstellensystem erstellt neue Einsatzobjekte auf Basis der Benutzereingaben, speichert diese in einer Liste und stellt die enthaltenen Daten in tabellarischer Form dar. Änderungen an den Einsätzen, wie das Filtern, Sortieren oder Beenden, erfolgen zentral über die Klasse Leitstellensystem, greifen jedoch immer auf die zugrunde liegenden Einsatzobjekte zu.
Durch dieses Zusammenspiel bleibt die Datenstruktur klar vom Ablauf und der Oberfläche getrennt, während gleichzeitig eine einfache und übersichtliche Verwaltung der Einsätze möglich ist.

