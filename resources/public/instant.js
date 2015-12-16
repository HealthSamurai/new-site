$("body").on( "click", "a", function (ev) {
    ev.preventDefault();
    var href = $(this).attr('href');
    $('#main').load(href + ' #wrap');
    window.history.pushState( {} , href, href );

    return false;
});
