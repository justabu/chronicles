tinyMCE.init({
	// General options
	mode : "textareas",
	theme : "advanced",
	plugins: "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,wordcount,advlist,autosave",
    //plugins : "style,save,advimage,advlink,emotions,iespell,inlinepopups,media,searchreplace,noneditable,visualchars,xhtmlxtras,wordcount,advlist",

	// Theme options
	theme_advanced_buttons1 : "bold,italic,underline,strikethrough,justifyleft,justifycenter,justifyright,justifyfull,fontselect,fontsizeselect,bullist,numlist,blockquote,link,image,code,forecolor,backcolor,hr,charmap,emotions,media,fullscreen",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	theme_advanced_buttons4 : "",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	//theme_advanced_statusbar_location : "bottom",
	theme_advanced_resizing : true,
	//theme_advanced_buttons1 : "fontselect,fontsizeselect,bold,italic,underline,strikethrough,|,sub,sup,|,undo,redo,emotions,|,ltr,rtl,code",
	//theme_advanced_buttons2 : "justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,outdent,indent,bullist,numlist,|,forecolor,backcolor,|,anchor,link,unlink,image",
	//theme_advanced_buttons3 : "",
	//theme_advanced_toolbar_location : "top",
	//theme_advanced_toolbar_align : "left",
	//theme_advanced_statusbar_location : "bottom",
	//theme_advanced_resizing : true,

	// Example content CSS (should be your site CSS)
	//content_css : "/chronicles/css/chronicles.css",

	// Drop lists for link/image/media/template dialogs
//	template_external_list_url : "lists/template_list.js",
//	external_link_list_url : "lists/link_list.js",
//	external_image_list_url : "lists/image_list.js",
//	media_external_list_url : "lists/media_list.js",
	
    relative_urls : false,
	remove_script_host : true,
	document_base_url : "/chronicles/",
	
	width: '95%',
	theme_advanced_resizing: false,

});
