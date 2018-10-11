$(document).ready(function () {

//    //desplegable botón reportes del menú prinicipal
//    $('.dropdown-toggle').hover(
//            function () {
//                $(this).find("ul").show();
//            },
//            function () {
//                $(this).find("ul").hide();
//            }
//    );

    $("nav .linkReload").click(function () {
        var ubicacionHtml = $(this).data("loadhtml")
        $("#subPageContainer").remove();
        $("main").load("/html/"+ubicacionHtml);
    });
});