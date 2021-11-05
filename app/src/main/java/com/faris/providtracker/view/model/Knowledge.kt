package com.faris.providtracker.view.model

import com.faris.providtracker.R

data class Knowledge(
    val image: Int,
    val name: String,
    val desc: String
) {

    companion object {
        fun getKnowledges() = listOf(
            Knowledge(
                R.drawable.mask,
                "Kenakan Masker dan Face Shield",
                "Hal paling utama untuk menekan angka penyebaran virus adalah dengan mengenakan masker. Kamu juga bisa menggunakan face shield sebagai tambahan, karena hal ini dapat membantu mengurangi peluang kamu untuk menyentuh wajah dan masker saat digunakan.\n" +
                        "\n" +
                        "Usahakan untuk mengganti masker setiap 4-6 jam sekali dan buang ke tempat sampah setelah selesai digunakan. Kemudian, jangan lupa cuci face shield setelah digunakan dengan air mengalir serta sabun agar tetap steril, ya!"
            ),
            Knowledge(
                R.drawable.mask,
                " Jangan Lepas Masker Kecuali Saat Makan dan Minum",
                "Hal yang satu ini sering terlupakan bagi sebagian orang. Terkadang, kita membuka masker karena merasa gerah dan susah bernapas. Padahal, masker tetap harus digunakan saat beraktivitas dan berbicara dengan orang lain.\n" +
                        "\n" +
                        "Catat, ya, kamu boleh melepas masker saat makan dan minum aja. Nah, tapi, kamu juga harus meletakkan masker di tempat yang aman dan tidak terkontaminasi. Gunakan tisu untuk meng-cover masker dan letakkan di dalam tas atau di tempat aman lainnya."
            ),
            Knowledge(
                R.drawable.handsani,
                "Bawa Corona Finger Hand Extension dan Hand Sanitizer",
                "Corona finger hand extension adalah salah satu inovasi yang hadir karena kebutuhan masyarakat. Corona finger merupakan alat bantu berukuran kecil yang bisa kamu gunakan untuk mengurangi sentuhan tangan terhadap benda sekitar, seperti menekan tombol PIN di ATM, memencet lift, membuka pintu, dan sebagainya.\n" +
                        "\n" +
                        "Walaupun ada inovasi corona finger, jangan lupa untuk selalu membawa dan menggunakan hand sanitizer agar tangan tetap bersih."
            ),
            Knowledge(
                R.drawable.ewallet,
                "Gunakan Alternatif Pembayaran",
                "Pembayaran non tunai baik menggunakan kartu maupun electronic cash adalah jalan terbaik untuk mengurangi kontak fisik dengan banyak orang dibandingkan pembayaran dengan uang tunai."
            ),
            Knowledge(
                R.drawable.bath,
                "Bersih-bersih Setelah dari Luar Rumah",
                "Setelah keluar rumah, jangan lupa membersihkan alas kaki yang kamu gunakan dengan cairan disinfektan, air mengalir, dan sabun. Lalu bersihkan juga barang yang kamu bawa sebelum membawanya masuk ke rumah.\n" +
                        "\n" +
                        "Kemudian, segeralah mandi dan keramas untuk membersihkan kuman dan partikel-partikel yang menempel di pakaian dan badan kamu. Lalu, jangan lupa untuk segera mencuci pakaian yang dikenakan tadi dengan detergent, ya!"
            )
        )
    }
}