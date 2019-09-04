<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Subasta japonesa</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">
    
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  </head>
  
  <body>
    <script>
        refreshAuctionInformation = function() {
            location.reload();  
        };
    
        continueInTheAuction = function(auctionId, buyerId) {
            window.close();
            window.open('http://localhost:8111/openbravo/auction/buyer/continue_in_the_auction?auction_id=' + auctionId + '&buyer_id=' + buyerId);
        };
        
        leaveAuction = function(auctionId, buyerId) {
            window.close();
            window.open('http://localhost:8111/openbravo/auction/buyer/leave_auction?auction_id=' + auctionId + '&buyer_id=' + buyerId);
        };
    </script>
  
    <div class="header" style="border:1.7px solid #adc289;">
        <div align="right">
            <img src="http://localhost:8080/openbravo/utility/ShowImageLogo?logo=yourcompanymenu" align="right" name="isc_16main" border="0" suppress="TRUE" draggable="true">
            <a href="http://www.openbravo.com/product/erp/professional/" target="_new">
                <img src="http://localhost:8080/openbravo/utility/GetOpenbravoLogo.png" width="130" height="32" align="right" name="isc_18main" border="0" suppress="TRUE" draggable="true">
            </a>
        </div>
    
        <div class="header-right" style="padding: 20px 10px; border:5px solid white;">
        </div>
    </div>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-4" style="background-color:#e5e5e5; border:5px solid white;">
                        <h3 class="font-weight-bold">
                            <b>Información subasta</b>
                        </h3>
                        <ol class="breadcrumb" style="background-color:#adc289;">
                            <li class="breadcrumb-item">
                                <b>Tipo de subasta</b>
                            </li>
                        </ol>
                        <ul style="list-style-type:none;">
                            <li>${auction.auctionType.auctionTypeName}</li>
                        </ul>  
                        <ol class="breadcrumb" style="background-color:#adc289;">
                            <li class="breadcrumb-item">
                                <b>Precio actual</b>
                            </li>
                        </ol>
                        <ul style="list-style-type:none;">
                            <li>${auction.currentPrice} €</li>
                        </ul>  
                        <ol class="breadcrumb" style="background-color:#adc289;">
                            <li class="breadcrumb-item">
                                <b>Bien subastado</b>
                            </li>
                        </ol>
                        <ul>
                            <li>Nombre: ${auction.item.name}</li>
                            <li>Categoría: ${auction.item.category}</li>
                            <li>Descripción: ${auction.item.description}</li>
                            <li>Volumen: ${auction.item.volume}</li>
                            <li>Peso: ${auction.item.weight}</li>
                        </ul> 
                        <ol class="breadcrumb" style="background-color:#adc289;">
                            <li class="breadcrumb-item">
                                <b>Información adicional</b>
                            </li>
                        </ol>
                        <ul style="list-style-type:none;">
                            <li>${auction.additionalInformation}</li>
                        </ul>
                        <ol class="breadcrumb" style="background-color:#adc289;">
                            <li class="breadcrumb-item">
                                <b>Fecha de cierre</b>
                            </li>
                        </ol>
                        <ul style="list-style-type:none;">
                            <li>${auction.deadLine?datetime}</li>
                        </ul>
                    </div>
                    <div class="col-md-4" style="background-color:#e5e5e5; border:5px solid white;">
                        <h3 class="font-weight-bold">
                            <b>Lista de compradores</b>
                        </h3>
                        <table class="table table-sm">
                            <tbody id="buyers_info">
                                <tr>
                                    <th>Email</th>
                                </tr>
                                <#list auction.auctionBuyers as buyer>
                                    <#if buyer.email == buyer_email>
                                        <tr class="table-info">
                                    <#else>
                                        <tr class="table-warning">
                                    </#if>
                                        <td id = "${buyer.email}">
                                            ${buyer.email}
                                        </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="col-md-4" style="background-color:#e5e5e5; border:5px solid white;">
                        <h3 class="font-weight-bold">
                            <b>Acciones</b>
                        </h3
                    
                        <div class="form-group">
                            <table class="table table-sm">
                                <tbody>
                                    <tr>
                                        <td>
                                            <button type="button" class="btn" id="continue_in_the_auction_button" style="background-color: #ef9734; color: #FFFFFF;" onClick="continueInTheAuction(${auction_id}, ${buyer_id});">
                                                <span class="glyphicon glyphicon-floppy-disk"></span> Continuar
                                            </button>
                                        </td>
                                        
                                        <td>
                                            <button type="button" class="btn" id="leave_auction_button" style="background-color: #de0000; color: #FFFFFF;" onClick="leaveAuction(${auction_id}, ${buyer_id});">
                                                <span class="glyphicon glyphicon-floppy-disk"></span> Abandonar
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <button type="button" class="btn" id="update_auction_info_button" style="background-color:#42700f; color: #FFFFFF;" onClick="refreshAuctionInformation();">
                                                Actualizar información
                                            </button>
                                        </td>
                                        <td>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>
