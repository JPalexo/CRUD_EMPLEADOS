# Branching Strategy (GitFlow)

## Ramas base

- `main`: solo código probado y listo para producción.
- `develop`: integración continua del desarrollo más reciente.

## Ramas de trabajo

- `feature/*`: nuevas funcionalidades. Se crean desde `develop` y se fusionan en `develop`.
- `release/*`: preparación de lanzamiento. Se crean desde `develop` y se fusionan en `main` y `develop`.
- `hotfix/*`: correcciones urgentes en producción. Se crean desde `main` y se fusionan en `main` y `develop`.

## Convenciones de nombres

- `feature/<id>-<descripcion-corta>`
  - Ejemplo: `feature/003-paginacion-empleados`
- `release/<version>`
  - Ejemplo: `release/1.0.0`
- `hotfix/<version>-<descripcion-corta>`
  - Ejemplo: `hotfix/1.0.1-fix-auth-root`

## Flujo recomendado

1. Crear feature desde `develop`.
2. Abrir PR hacia `develop`.
3. Al cerrar alcance de release, crear `release/*` desde `develop`.
4. Validar QA/UAT en `release/*`.
5. Fusionar `release/*` hacia `main` y etiquetar versión.
6. Fusionar la misma `release/*` de regreso a `develop`.
7. Para incidentes productivos, crear `hotfix/*` desde `main`.
8. Fusionar `hotfix/*` en `main` y también en `develop`.

## Comandos base

```bash
# Crear feature desde develop
git switch develop
git pull
git switch -c feature/003-paginacion-empleados

# Crear release desde develop
git switch develop
git pull
git switch -c release/1.0.0

# Crear hotfix desde main
git switch main
git pull
git switch -c hotfix/1.0.1-fix-auth-root
```

## Reglas de calidad sugeridas

- PR obligatorio para merge a `develop` y `main`.
- CI obligatorio en PR (`build + tests`).
- Prohibir push directo a `main`.
- Etiquetar releases en `main` (`v1.0.0`, `v1.0.1`, etc.).
