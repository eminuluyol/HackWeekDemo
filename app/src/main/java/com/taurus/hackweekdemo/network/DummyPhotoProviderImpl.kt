package com.taurus.hackweekdemo.network

import java.util.*

internal class DummyPhotoProviderImpl() : DummyPhotoProvider {

    private val dummyPhotoUrls = listOf(
            "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg",
            "https://media.ed.edmunds-media.com/dodge/challenger/2018/oem/2018_dodge_challenger_coupe_rt-scat-pack_fq_oem_1_717.jpg",
            "https://media.zigcdn.com/media/content/2016/Jul/2016-bmw-i8-zigwheels-india-photo-galary-m1_720x540.jpg",
            "https://images.cardekho.com/images/mycar/large/tata/tiago/marketing/Tata-Tiago.webp",
            "http://bdfjade.com/data/out/107/6116786-car-image.png",
            "https://cars.usnews.com/dims4/USNEWS/42b90e3/2147483647/resize/640x420%3E/format/jpeg/quality/85/?url=https%3A%2F%2Fcars.usnews.com%2Fstatic%2Fimages%2Farticle%2F201703%2F126752%2F17_Civic_Sedan_37_6_640x420.jpg",
            "https://icdn1.digitaltrends.com/image/2-5-million-bugatti-chiron_001-720x720.jpg?ver=1.jpg",
            "http://cdn1.carbuyer.co.uk/sites/carbuyer_d7/files/styles/16x9_720/public/2017/12/aston-martin-vantage-39_2.jpg?itok=4yRAJFRg",
            "http://cdn1.carbuyer.co.uk/sites/carbuyer_d7/files/styles/16x9_720/public/2017/12/alfa-romeo-stelvio-quadrifoglio.jpg?itok=6esA2FSf",
            "https://autowise.com/wp-content/uploads/2015/03/mump_0810_01_z-1967_mustang_fastback-front_view.jpg"
    )

    override fun provideDummyPhoto(): String {
        val random = Random()
        return dummyPhotoUrls[random.nextInt(10)]
    }

}
