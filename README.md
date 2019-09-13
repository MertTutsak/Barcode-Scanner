### Build bilgileri

- `Android Studio` 3.6 sürümü ve üzerinde olmalı. 
- `Gradle version` 3.6.0-alpha06
- `Gradle distribution version` gradle-5.5.1-bin 
- `CompileSdkVersion` 29
- `BuildToolsVersion` 29.0.1
- `MinSdkVersion` 21
- `TargetSdkVersion` 29
- `KotlinVersion` 1.3.41

### Mimari bilgileri 

    (package : di)
- ActivityBuilder :  dagger'ın activity'i oluşturması için açılan yüm activityleri modulleri ile aynı formatta yazılmalıdır.
- AppComponent : açılan yeni modülü buraya modul olarak eklenmeli
- AppModule : Uygulamanın herhangi bir yerinde bir kere oluşturulup devam etmesi gereken clasları burada  aynı formatta oluşturabilirsiniz

    (package: api)
- ApiHelper : retrofit callbacklerinin oluşturulduğu  inteface
- AppApiHelper : interactor'ün bağlantı yaptığı classdır her servis sogusu için ayrı metod açılarak devam edilir aynı formatta yazılabilir. ilerde daha fazla özelleştirilebilir.
- ApiDisposable :rxjavanın responsu handle edip Disposable return etmesi için geliştirlmiş classtır.
- ApiError : Handle da sorun oluşursa parse ,connetion gibi hatalırı barındıran class


    (örnek uı package : main)
- interactor: bu view  için gereken servis metodlarını burası üzerinden haberleştirmemiz gerekmekte  AppApiHelper daki metodu kullanabilmeniz için ilk olarak burada tanımlamalısınız.
- presneter :interactor'e bağlanarak servis isteğinde bulunabilirsiniz. classa constructor da interactor  geliyor oradan servise çıkabilirsiniz.
- view :
- ....ActivityModule : bu sayfada kullanılacak interactor presneter gibi  classları daggera bildirdiğimiz modul

### Proje bilgileri
- lds-starter-android v1.0.3 projesinden forklanmıştır.
- Paket adı com.vodafone.hrall olarak değilştirilip eski proje ile aynı paket adı olacak şekilde düzenlenmiştir.
- İlk versiyon bilgileri `versionCode 10` ve `versionName "5.0"` olacak şekilde düzenlenmiştir. Bu şekilde eski projeye update çıkılabilmesi sağlanacaktır.

### Splash Sayfası
- Splash Activity içinde açılacaktır.
- ViewGroup'u MotionLayout'tur. Bunun nedeni login ve otp sayfaları arasında ki animasyon geçişleri için yapılmıştır.
- BaseNoAnimActivity sınıfından kalıtım alınmıştır.
- Açılışta internet bağlantısı kontrol edilecektir. Eğer internet yoksa showConnectionError diyaloğu gösterilecektir. İnternet bağlantısı kurulduktan sonra tamama tıklanınca akışa devam edecektir.
- Eğer sharedpreferencesde token tutuluyorsa token ile giriş yapacaktır. Tutulmuyorsa  Login Sayfası açılacaktır.

### Login Sayfası
- Custom bir sınıf oluşturulmuştur. ViewGroup'u RelativeLayout'tur. SplashActivity'e entegre edilmiştir.
- Splash sayfasında token yoksa ise showLogin metodu ile açılacaktır.
- Daha önceden giriş yapılmış ve beni hatırla aktif edilmiş ise alanlar dolu gelecektir.
- 'Kullanıcı Adı' ve 'Şifre' alanları doldurulana kadar 'Şifre Gönder' butonu disable olacaktır. Alanlar doldurulduktan sonra enable yapılacaktır.
- 'Şifre Gönder' butonuna tıklandığında butonun sağ tarafında ki loading animasyon başlayacaktır. Kullanıcı adı ve şifre ile servise çıkılacaktır. Servis telefona bir mesaj gönderecektir.
- Loading animasyonu başlatmak için spinKit UI'nı VISIBLE yapılması yeterlidir. durdurmak için spinKit'i GONE yapması yeterlidir.

### OTP Sayfası
- Custom bir sınıf oluşturulmuştur. ViewGroup'u RelativeLayout'tur. SplashActivity'e entegre edilmiştir.
- Login başarılı bir şekilde gerçekleştiğinde showOtp() metodu ile açılacaktır..
- İlk açılışta 'Giriş Yap' alanı disable olarak açılacaktır.
- Şifrenin girilmesi için 180 saniye tanımlanmıştır. Bu süre bittiğinde geri sayım bitecek tekrar gönder butonu gelecektir. Tıklandığında username ve password ile Login isteği tekrar atılacaktır.
- Şifre alanı en fazla 6 karakter girilecektir.
- Şifre alanına 6 karakter girildiğinde 'Giriş Yap' butona enable olacaktır. Tıklandığında servise ValidateOTP isteği gönderilecektir. Başarılı dönerse token sharedpreferencese kaydedilecektir ardından batch data çekilecektir.
- Tüm işlemler başarıyla gerçekleşirse dashboard sayfasına girecektir.

### Dashboard Sayfası
- Dashboard Activity CoordinatorLayout içinde açılacaktır. Bunun nedeni bottomsheet açılacağı içindir.
- ViewGroup'u ConstraintLayout'tur.
- Sağ üstte o anki saate göre günün vaktini yazacaktır. Gün 4'e(Sabah,Güniçi,Akşam,Gece) bölünmüştür.
  - Çıkacak mesaj örneği -> Günaydın/İyi Günler/İyi Akşamlar/İyi Geceler
- Kullanıcı ismini Session'dan alınacaktır. Session boş ise hiçbir şey yazmayacaktır.
  - Dolu Session -> İyi Akşamlar, Emrah Çetiner
  - Boş Session -> İyi Akşamlar
- Çıkış butonuna tıklandığında session ve tokenı silip Splash'i başlatacaktır.

### Otopark
- İlk durumunu batchdata'dan alacaktır.
- Tıklandığında servise GetCarParkStatus isteği atılacaktır. Servisten dönüşe göre durumu değişecektir.
- Servisten çok kısa zamanda döneceğini ve durumun değişmeyeceğini varsayarsak kullanıcı değişiklik olduğunu anlayamayacağı için durumun güncellenmesi 1 saniye geciktirilmiştir. Bu sayede loading animasyon gözkebilecektir.
- Loading animasyonu başlatmak için spinKit UI'nı VISIBLE yapılması yeterlidir. durdurmak için spinKit'i GONE yapması yeterlidir.

### Meeting Sayfası
- Sayfa Fragment yapısından oluşmaktadır.
- Activity'de sol üstte back butonu bulunmaktadır. Bu buton onbackpress ile aynı işlevi yapacaktır.

### Meeting/Search Sayfası
- Sayfada 3 adet cardview ve search için bir button bulunmaktadır.
- En üstte ki cardview ile hangi bina ile devam edileceği seçilecek. Bu seçimleri bottomsheet ile gösterilecektir.
- En ortada ki cardview ile toplantı günü seçilecektir. Haftasonları disable olarak gösterilecektir. Kullanıcının olduğu günden sonra ki 8 gün gösterilecektir.
- En altta ki cardview ile toplantının saatleri seçilecektir.Toplantı başlangıç saati 23:45 seçilmiş ise cardview'de 23:45 - 00:00 yqazacaktır. Toplantı saati 1 saatten fazla seçtiğinde uyarı verecektir.
- Tüm seçimler tamamlandığında "Ara" butonu enable olacaktır.
- Butona tıklanıldığında servise GetMeetingRoomList isteği atarak uygun olan saatler çekilecektir. Eğer uygun saat yok ise uyarı verilecektir. Uygun saat bulunursa activity üzerinde create fragmentini stack'e ekleyeceğiz.
- Bottomsheetleri kullanmak için yeni bir instance yaratarak show metotudu çağırıyoruz.

### Meeting/Create Sayfası
- Uygun olan saatler recyclerviewde listelenecektir. Buton listenin en altına gelecektir.
- Seçim yapıldığında "Rezervasyon Oluştur" butonu enable olacaktır.
- Butona tıklandığında seçilen saat aralığı ile oda bilgisi MeetingAddFragment newInstance metotuna parametre olarak gönderilecektir. newInstance metotu aldığı parametrelerde ki değerleri bundle'a kaydedecektir.

### Meeting/Add Sayfası
- Gerekli bilgiler argumentsden alınarak CardView içinde ki gerekli alanlara yazacaktır.
- Toplantı konusunun yazılması zorunlu alandır. Açıklaması ise zorunlu bir alan değildir.
- Toplantı konusu yazıldığı takdirde "Rezervasyon Yap" butonuna enable olacaktır.
- "Rezervasyon Yap" butonuna tıklandığında servise SaveMeetingRoom isteği atılacaktır. Başarılı ise çıkan diyalogta tamama ve çarpıya tıkladığında anasayfaya yönlenecektir. Başarısız olduysa tekrardan toplantı oluşturması için search fragmentına ydönecektir.
- Diyaloglar bottomsheet olacaktır. ReservationStateBottomSheet sınıfı çağırılacaktır.
- ReservationStateBottomSheet'i oluştururken çağırılan newInstance parametresine true değeri verince toplantı bilgileri gözükecektir. False değeri verilirse uyarı yazısı gösterilecektir.

### Gallagher
- `SDK Version` 13.2.013
- Gallagher'a giriş yapıldığı mobileAccess.credentials'ın size'ından bulunacaktor. Size 0 ise 'Giriş yap' gözükecektir. Size 0'dan yüskek ise 'Kilit aç' gözükecektir.
- Giriş yapılması için kullanıcının mailine gönderilen aktivasyon kodunnu girecektir. Eğer kod başarılıysa kullanıcıdan telefonun PIN kodunu girmesi istenecektir.
- Giriş başarısız ise SDK'den dönen hataya diyalog olarak ekrand gösterilecektir.
- Giriş başarılı ise Kullanıcıdan lokasyon ve bluetooth'unu açması istenecektir. Eğer gerekliyse izinlerini alacaktır.
- SDK bluetooth ile tarama yapacaktır ve background servislerde çalışabilecektir.
- Bluetooth açtırma SDK'ya register olunmuşsa en başta ve ya register olunduğunda istenecektir. Sonra ki tercihler kullanıya bırakılacaktır.






