# 🔐 Android Authentication Flow - Modern Architecture

Sistema de autenticación completo implementando las mejores prácticas de Android moderno.

## 🏗️ Arquitectura & Stack
- **Pattern:** MVVM + Clean Architecture
- **UI:** Jetpack Compose con Material 3
- **Navigation:** Navigation Compose (Type-Safe)
- **DI:** Hilt
- **Async:** Kotlin Coroutines + Flow
- **Security:** EncryptedSharedPreferences para tokens
- **Network:** Retrofit + OkHttp

## 🎯 Features Implementados
✅ Login/Register con validación
✅ Access Token + Refresh Token automático
✅ Navegación condicional según estado de auth
✅ Manejo de estados con StateFlow
✅ Persistencia segura de credenciales
✅ Token refresh automático en 401
