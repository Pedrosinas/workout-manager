# Workout Manager

Aplicativo Android para gerenciar progressão de carga na academia, salvar e acompanhar a evolução.
100% offline.

---
## Tecnologias Utilizadas

- **Linguagem principal**: Kotlin 1.9
- **UI**: Jetpack Compose (Material 3)
- **Persistência**: Room Database 2.6.1 (SQLite)
- **Processamento de Anotações**: KSP (Kotlin Symbol Processing) 1.9.23
- **Concorrência e Estado**: Kotlin Coroutines & StateFlow
- **Arquitetura**: MVVM (Model-View-ViewModel) + Clean Architecture
- **Navegação**: Navigation Compose 2.7.7
- **Build System**: Gradle 8.6 + Version Catalogs (`libs.versions.toml`)
- **Compatibilidade**: Android SDK 34 (Android 14) / Min SDK 26 (Android 8.0+)

---

## Estrutura de Pastas

```text
workout-manager/
├── gradle/
│   ├── wrapper/
│   │   └── gradle-wrapper.properties
│   └── libs.versions.toml
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/workoutmanager/
│           ├── data/
│           │   ├── local/ (dao, entity, WorkoutDatabase.kt)
│           │   └── repository/ (WorkoutRepository.kt)
│           ├── ui/
│           │   ├── components/ (WorkoutDialogs.kt)
│           │   ├── navigation/ (WorkoutNavHost.kt)
│           │   ├── screens/ (WorkoutListScreen.kt, WorkoutDetailScreen.kt)
│           │   ├── theme/ (Theme.kt)
│           │   └── viewmodel/ (WorkoutListViewModel.kt, WorkoutDetailViewModel.kt)
│           └── MainActivity.kt
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── local.properties
