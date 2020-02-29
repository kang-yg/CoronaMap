* Asyctask의 onPreExecute(), doInBackground() 메소드에서 Firebase에 저장된 데이터를 두개의 메소드를 통해 가져올 경우 두 메소드의 순서를 보장받지 못한다, → 데이터를 가져오는 두 메소드를 하나로 합침<br>

* Firebase에서 데이터를 가져오는 리스너는 자체적으로 비동기를 지원하므로 Asyctask와 같은 비동이 처리를 따로 하지 않아도 된다.<br>

* onMapReady()의 호출을 늦출 수 없다. 따라서 Firebase에서 데이터를 가져올 때 마커를 추가해야 한다.<br>

* Firebase에서 GeoPoint타입의 데이터를 가져와서 위도와 경도를 각각 Double타입에 저장을 한 후 LatLng타입으로 저장을 해서 사용이 가능하다.<br>

* java.time패키지의 ofEpochMilli()메소드는 Android API레벨이 26이상이어야 한다.<br>

* Seconds와 milliSeconds가 다르다. 그러므로 Date타입으로 포맷할 때 DateFormat.format()의 매개변수를 확인해야 한다.<br>
  https://stackoverflow.com/a/7954038/12355451<br>
    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(milliSeconds);
    return formatter.format(calendar.getTime());

* 동일한 Firebase Firestore 내의 다른 Collection에서 데이터를 가져올 경우 FirebaseFirestore객체를 여러번 사용해 데이터를 가져오게 된다. 이 때 addOnCompleteListener를 사용해도 Collection의 순서를 보장받지 못한다.<br>
→ Firebase Firestore Collection과 Field를 수정해서 해결.<br>

* GoogleMap Polyline<br>
https://stackoverflow.com/a/16631189/12355451<br>
PolylineOptions에 LatLan을 추가할 때 좌표 하나하나를 추가하지 않고 ArrayList에 좌표를 저장하고 PolylineOptions().addAll(arrayList)도 가능하다.<br>

* marker의 Color변경<br>
https://stackoverflow.com/a/27322075/12355451<br>
https://stackoverflow.com/a/3732073/12355451<br>
https://en.wikipedia.org/wiki/Hue#Computing_hue_from_RGB<br>

BitmapDescriptorFactory에 정의되어 있는 10가지 색은 아래와 같이 사용하면 된다.<br>
    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

BitmapDescriptorFactory에 정의되어 있지 않은 색을 사용하기 위해서는<br>
BitmapDescriptorFactory.defaultMarker()에 16진수를 전달한다. 전달한 16진수를 RGB값으로 변환해서 반환해 줘야 한다.<br>

    markerOption.icon(BitmapDescriptorFactory.defaultMarker(getHue("33FFFF")))

    fun getHue(color: String): Float {
        var r: Int = Integer.parseInt(color.substring(0, 2), 16) / 255
        var g: Int = Integer.parseInt(color.substring(2, 4), 16) / 255
        var b: Int = Integer.parseInt(color.substring(4, 6), 16) / 255

        var hue: Int? = null
        if ((r >= g) && (g >= b)) {
            hue = 60 * (g - b) / (r - b)
        } else if ((g > r) && (r >= b)) {
            hue = 60 * (2 - (r - b) / (g - b))
        } else if ((g >= b) && (b > r)) {
            hue = 60 * (2 + (b - r) / (g - r))
        } else if ((b > g) && (g > r)) {
            hue = 60 * (4 - (g - r) / (b - r))
        } else if ((b > r) && (r >= g)) {
            hue = 60 * (4 + (r - g) / (b - g))
        } else {
            hue = 60 * (6 - (b - g) / (r - g))
        }
        return hue!!.toFloat()
    }

* GoogleMap은 XML의 <Fragment>태그로 정의되어 있다. 이 GoogleMap위에 위젯을 올리리고 싶다면 <FrameLayout>으로 GoogleMap과 위젯을 감싸주면 된다.<br>
https://stackoverflow.com/a/22095238/12355451<br>