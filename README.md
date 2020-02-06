* Asyctask의 onPreExecute(), doInBackground() 메소드에서 Firebase에 저장된 데이터를 두개의 메소드를 통해 가져올 경우 두 메소드의 순서를 보장받지 못한다, → 데이터를 가져오는 두 메소드를 하나로 합침<br>

* Firebase에서 데이터를 가져오는 리스너는 자체적으로 비동기를 지원하므로 Asyctask와 같은 비동이 처리를 따로 하지 않아도 된다.<br>

* onMapReady()의 호출을 늦출 수 없다. 따라서 Firebase에서 데이터를 가져올 때 마커를 추가해야 한다.