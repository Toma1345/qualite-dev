# TP Exercice 2 : Corriger les problèmes de qualité et introduire des tests

## Tâche 1 : Compléter les commentaires et la Javadoc
Réalisée.

## Tâche 5 : Questions
1. Quelle est la différence entre les tests unitaires et les tests d'intégration ?
    > **Unitaires** : Vérifient une petite unité de code (exemple : une classe) de manière isolée. Les dépendances sont simulées (mocks). Très rapides.
    > **Intégration** : Vérifient que plusieurs composants (code, base de données, services externes) fonctionnement correctement ensemble. Plus lents.

2. Est-il pertinent de systématiquement couvrir 100% de la base de code par des tests ? Expliquer votre réponse.
    > **Non**. Le coût et l'effort pour tester le code trivial ou les chemins d'erreur marginaux n'apportent presque aucun bénéfice. Il est plus important de concentrer l'effort sur la qualité et la couverture de la **logique métier critique**.

3. Quels avantages apporte une architecture en couches d'oignon dans la couverture des tests ? *Expliquer votre réponse en prenant pour exemple ce que vous avez pu observer sur l'écriture des tests de la tâche 3*.
    > L'architecture d'oignon **isole le coeur métier** (la logique) de l'infrastructure (BDD, API).  
    > **Avantage** : Cela permet de tester la logique métier (couche `application`) de manière unitaire et ultra-rapide en simulant les dépendances d'infrastructure sans jamais démarrer une base de données réelle.

4. Expliquer la nomenclature des packages ``infra``, ``application``, ``jpa``, ``web``, ``client``, ``model``.
    > ``model`` : Le cœur du domaine métier (agrégats, entités objets de valeur).
    > ``application`` : Logique des cas d'usage (les services qui orchestrent la logique métier).
    > ``infra`` : Le code d'infrastructure (tout ce qui est externe au cœur).
    > ``jpa`` : Adaptateur de persistance (gestion de la base de données via JPA/Hibernate).
    > ``web`` : Adaptateur d'exposition (contrôleurs REST, DTOs de l'API).
    > ``client`` : Adaptateur d'appel externe (le code pour interagir avec d'autres microservices ou APIs).