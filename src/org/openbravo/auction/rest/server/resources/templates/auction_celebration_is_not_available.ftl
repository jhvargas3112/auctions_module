<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Subasta</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    
  </head>
  <body>
    <script>
        buyerAuthentication = function() {
            var auction_id = ${auction_id};
            var buyer_id = document.getElementById("buyer_id").value;
            
            try {
                request = new XMLHttpRequest();
                request.open('GET', 'http://localhost:8111/openbravo/auction/celebration?auction_id=' + auction_id + '&buyer_id=' + buyer_id, false);
                request.send();
            } catch (err) {
                throw 'NETWORK_ERROR';
            }
            
            window.close()
            window.open('http://localhost:8111/openbravo/auction/celebration?auction_id=' + auction_id + '&buyer_id=' + buyer_id);
        }
    </script>
    <div class="container h-80">
        <div class="row align-items-center">
            <div class="col-3 mx-auto">
                <div class="text-center">
                    <a href="http://www.openbravo.com/product/erp/professional/" target="_new">
                <img src="http://localhost:8080/openbravo/utility/GetOpenbravoLogo.png" width="130" height="32" align="center" name="isc_18main" border="0" suppress="TRUE" draggable="true">
            </a>
                    <p id="profile-name" class="profile-name-card"></p>
                        <input type="input" name="buyer_id" id="buyer_id" class="form-control form-group" placeholder="Código de acceso" required autofocus>
                        <p>La subasta aún no ha empezado a celebrarse. Puede unirse a ella a partir del ${celebration_date?datetime}.</p>
                        <button class="btn btn-block btn-signin" type="submit" style="background-color:#ef9734; color: #FFFFFF;" onClick="buyerAuthentication()">Acceder a la subasta</button>
                </div>
            </div>
        </div>
    </div>
  </body>
</html>
