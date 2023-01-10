# tms-ktor-template

Kan brukes som utgangspunkt for å opprette nye Ktor-apper for Team Min Side.

# Tilpass repo-et

## Tilpass navn

1. Søk etter og erstatt `tms-ktor-template` med det som skal være navnet på den nye appen.
2. Endre navnet på mappen `src/main/kotlin/no/nav/tms/template` til noe som passer for den nye appen. 
3. Tilpasse pakkenavnene, søk etter og erstatt `tms.template` med `tms.<ny mappestruktur>`.


## Legg til autentisering

De fleste apper i vårt domene trenger enten å validere tokenx-tokens eller å integrere mot ID-Porten.

Benytt deg av dependencies fra `implementation(Tms.KtorTokenSupport.<modul>)` for å komme raskt i gang.

Referer til repo [navikt/tms-ktor-token-support](https://github.com/navikt/tms-ktor-token-support) for mer info om bruk.


## Velg riktig ingress

Templaten kommer konfigurert med to sett med ingresser. Det ene er ment for apper som skal nåes fra frontend, og
det andre for apper som kun skal nåes fra andre tjenester eller naisdevice:

- Skal nåes fra frontend: `https://person.(dev.)nav.no/<appnavn>`
- Skal kun nåes fra andre tjenester: `https://<appnavn>.(dev.)intern.nav.no/<appnavn>` 


## Workflows

Endre navn på mappen `.github/workflow_files` til `.github/workflows` for at github actions skal plukke dem opp.

Det er god sannsynlighet for at dette prosjektet henger etter workflows fra andre prosjekt. Husk å legge til prosjektet
i `pb-workflow-authority`.


# Kom i gang
1. Bygg tms-ktor-template ved å kjøre `gradle build`
2. Start appen lokalt ved å kjøre `gradle runServer`
3. Appen nås på `http://localhost:8101/tms-ktor-template`
   * F.eks. via `curl http://localhost:8101/tms-ktor-template/internal/isAlive`

# Henvendelser

Spørsmål knyttet til koden eller prosjektet kan stilles som issues her på github.

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #team-personbruker.
