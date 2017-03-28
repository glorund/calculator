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
        });
        </script>
        <script>
        function callSomeFunction() {
        var params = $('#input').val().split(",")
            $.ajax({
                type: "POST",
                url: "api/calc",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data) {
                    $('#result').text(data);
                },
                error: function (data) {
                    alert('fail' + data.statusCode);
                }
            });
        }
        </script>
    </head>

    <body>
        <div id="formula-value">Formula is </div>
        <div>
        Parameters <input id="input" type="text" placeholder="Specify Parameters"/> <button id="button" data-role="button"  onclick="callSomeFunction();">calculate</button>
        </div>
        <div>Result =<a id="result"></a></div>
    </body>
</html>
