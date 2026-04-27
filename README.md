# 📰 Awi201News - Tugas 6 Networking & REST API

**Identitas Pengembang:**
* **Nama:** Awi Septian Prasetyo
* **NIM:** 123140201
* **Mata Kuliah:** Pengembangan Aplikasi Mobile
* **Program Studi:** Teknik Informatika
* **Instansi:** Institut Teknologi Sumatera

---

## 🚀 Deskripsi Proyek
**Awi201News** adalah aplikasi **News Reader** berbasis **Compose Multiplatform** yang dibuat untuk memenuhi **Tugas Praktikum Minggu 6**. Aplikasi ini mengambil data artikel dari **public API**, menampilkan daftar berita dengan gambar, judul, dan deskripsi, menyediakan detail screen, mendukung pull to refresh, serta menangani loading, success, dan error states dengan arsitektur **Repository Pattern**.

Selain itu, aplikasi ini mengintegrasikan kembali **halaman Profile** dari tugas sebelumnya agar tampilan tetap konsisten dan lengkap.

---

## 🎯 Tujuan Tugas
1. Fetch berita dari public API.
2. Tampilkan list artikel dengan **title, description, dan image**.
3. Detail screen saat artikel di-klik.
4. Pull to refresh functionality.
5. Proper **loading, success, dan error states**.
6. Repository pattern untuk API calls.

---

## 📸 Hasil Screenshot

| **Dark Mode** | **Light Mode** |
| :---: | :---: |
| <img src="pam61.jpeg" width="350" alt="Dark Mode 1" /> | <img src="pam62.jpeg" width="350" alt="Light Mode 1" /> |
| <img src="https://github.com/user-attachments/assets/a456c371-114c-40f5-bc76-f313f6031316" width="350" alt="Dark Mode 2" /> | <img src="https://github.com/user-attachments/assets/4b29932d-ec9d-42cd-8e50-3e87ca5dc8be" width="350" alt="Light Mode 2" /> |

---

## ✨ Fitur Utama

* **News List**: Halaman utama menampilkan daftar artikel dalam bentuk card (gambar, judul, deskripsi, kategori).
* **Article Detail**: Halaman detail saat artikel ditekan, menampilkan konten lebih lengkap dan tombol kembali.
* **Pull to Refresh**: User dapat memperbarui daftar artikel dengan menarik layar ke bawah.
* **UI States**: Penanganan kondisi **Loading**, **Success**, dan **Error**.
* **Category Filter**: Filter berdasarkan kategori seperti All, Tech, Education, Campus, Lifestyle, dan General.
* **Category Search**: Fitur pencarian untuk menemukan artikel berdasarkan kategori dengan cepat.
* **Profile Screen**: Tab Profil yang mempertahankan desain dan identitas dari tugas sebelumnya.
* **Bottom Navigation**: Navigasi dua tab utama: **News** dan **Profile**.

---

## 🌐 API yang Digunakan
Aplikasi ini menggunakan **JSONPlaceholder** sebagai public API utama:
`https://jsonplaceholder.typicode.com/posts`

> **Catatan:** Karena JSONPlaceholder tidak menyediakan gambar asli, aplikasi menggunakan *placeholder image* agar visual tetap menarik di list maupun detail.

---

## 🛠️ Konsep & Arsitektur
Aplikasi ini menerapkan beberapa teknologi dan pola desain:
* **Ktor Client** & **HTTP GET Request**
* **Kotlinx Serialization**
* **Repository Pattern**, **ViewModel**, & **StateFlow**
* **UiState** (Loading, Success, Error)
* **Navigation Compose** & **Pull to Refresh**

### Struktur Folder
```text
composeApp/src/commonMain/kotlin/org/example/project/
├── data/
│   ├── Article.kt, ArticleDto.kt, UiState.kt
├── network/
│   ├── HttpClientFactory.kt, NewsApi.kt
├── repository/
│   └── NewsRepository.kt
├── viewmodel/
│   └── NewsViewModel.kt
├── navigation/
│   ├── Screen.kt, AppNavigation.kt, BottomNavBar.kt
├── components/
│   ├── ArticleCard.kt, LoadingView.kt, ErrorView.kt, EmptyView.kt
└── ui/
    └── screens/
        ├── NewsListScreen.kt, ArticleDetailScreen.kt, ProfileScreen.kt
```

---

## 🔄 Alur Navigasi
1.  **News**: Daftar artikel → Klik artikel → **Article Detail**.
2.  **Profile**: Membuka halaman profile lama.
3.  **Article Detail**: Tombol back → Kembali ke daftar News.

---

## ⚠️ Kendala Selama Pengerjaan
* Konfigurasi dependency **Ktor** pada Compose Multiplatform.
* Permission internet pada platform Android.
* Penyesuaian **API Material 3** dan layout bottom bar.
* Integrasi halaman Profile lama agar tetap konsisten.
* Pengaturan kategori secara manual karena keterbatasan API.

---

## ✅ Kesimpulan
Proyek **Awi201News** berhasil mengintegrasikan UI yang rapi dengan manajemen data dari API menggunakan **Ktor** dan **Repository Pattern**. Hal ini memperkuat pemahaman saya mengenai *state management*, *networking*, dan struktur kode yang terorganisir dalam pengembangan aplikasi mobile.

---
