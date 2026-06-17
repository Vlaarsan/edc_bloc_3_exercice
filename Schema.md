Schéma relationnel

👤 users

Un utilisateur peut :

créer des projets
rejoindre des projets
être assigné à des tâches
📁 projects

Un projet :

appartient à un user (owner)
contient des membres
contient des tâches
🔗 project_members

Table clé :

gère les rôles dans un projet

Rôles :

ADMIN
MEMBER
OBSERVER
📌 tasks

Une tâche :

appartient à un projet
peut être assignée à un user
a un statut + priorité
🕓 task_history

Historique des modifications (audit simple)