package com.taurus.hackweekdemo.network

import java.util.*

internal class DummyPhotoProviderImpl() : DummyPhotoProvider {

    private val dummyPhotoUrls = listOf<String>(
            "https://www.ford.com/cmslibs/content/dam/brand_ford/en_us/brand/legacy/nameplate/cars/18_mst_segment_landing_32.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg",
            "https://media.ed.edmunds-media.com/dodge/challenger/2018/oem/2018_dodge_challenger_coupe_rt-scat-pack_fq_oem_1_717.jpg",
            "https://media.zigcdn.com/media/content/2016/Jul/2016-bmw-i8-zigwheels-india-photo-galary-m1_720x540.jpg",
            "https://images.cardekho.com/images/mycar/large/tata/tiago/marketing/Tata-Tiago.webp",
            "http://bdfjade.com/data/out/107/6116786-car-image.png"
    )

    override fun provideDummyPhoto(): String {
        val random = Random()
        return dummyPhotoUrls[random.nextInt(5)]
    }

}
