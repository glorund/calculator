<!DOCTYPE html>
<html>
    <head>
        <title>Hello jQuery</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <!-- <script src="hello.js"></script> -->
        <script>
        $(document).ready(function() {
            $.ajax({
                url: "api/config"
            }).then(function(data) {
               $('#formula-value').append(data);
            });
            var data = [12.4,0.2,9876.5e-1];
            $.ajax({
                   type: "POST",
                   url: "api/calc",
                   data: JSON.stringify(data),
                   contentType: "application/json; charset=utf-8",
                   dataType: "json",
                   success: function (data) {
                	   document.getElementById('result').innerHTML=data;
                   },

                   error: function (data) {
                       // error handler
                       console.log(jqXHR);
                       alert('fail' + status.code);
                   }
            });
        });
        </script>
        <script>
        $('#btn').click(function(){
        	$.ajax({
                url: "api/config"
            }).then(function(data) {
               $('#result').append(data);
            });
        });
        </script>
    </head>

    <body>
        <div>
            <p id="formula-value">The formula is </p>
        </div>
        Paramaters <input id="inp" type="text" placeholder="Enter Paramaters" data-bind="value: arguments"  />
        <button id="btn">calculate</button>
        <div>Result<div id="result"></div></div>
    </body>
</html>
