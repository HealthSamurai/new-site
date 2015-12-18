$("body").on( "click", "a", function (ev) {
    ev.preventDefault();
    var href = $(this).attr('href');
    $('#main').load(href + ' #wrap');
    window.history.pushState( {} , href, href );
    window.scrollTo(0,0);
    return false;
});

$(window).on('popstate', function(ev){
    $('#main').load(window.location.href + ' #wrap');
    window.scrollTo(0,0);

});
