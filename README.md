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