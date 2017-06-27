function main(config) {
	this.ico = config.ico;
}
main.prototype = {
	ico:false,
	config: function(list) {
		var icoText = '';
		document.getElementById("main").src=list[0].items[0].href;
		for (var k in list) {
			var title = list[k].text;
			var data = list[k].items;
			var li = document.createElement("li");
			var a = document.createElement("a");
			var ul = document.createElement("ul");
			ul.className = 'nav nav-second-level';
			if (this.ico== true) {
				icoText = '<i class="fa ' + list[k].ico + '"></i>';
			}
			a.innerHTML = icoText + '<span class="nav-label">' + title + '</span><span class="fa arrow"></span>'
			li.appendChild(a);
			for (var n in data) {
				var lis = document.createElement("li");
				lis.innerHTML = '<a class="J_menuItem" href="' + data[n].href + '" data-index="' +k+""+n + '">' + data[n].text + '</a>'
				ul.appendChild(lis);
			}
			li.appendChild(ul);
			document.getElementById("side-menu").appendChild(li);
		}
	}
};