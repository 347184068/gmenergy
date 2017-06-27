var config = [{
	text: '用户群组',
	ico: 'fa-home',
	items: [{
		text: '学生管理',
		href: '../userController/student'
	}]
}, {
	text: '志愿专栏',
	ico: 'fa-bar-chart-o',
	items: [{
		text: '志愿专栏',
		href: '../schoolController/zyzl'
	}]
}, {
	text: '院校专业',
	ico: 'fa-envelope',
	items: [{
		text: '院校专业',
		href: '../schoolController/yxzy'
	}, {
		text: '专业分数',
		href: '../schoolController/zyfs'
	}, {
		text: '专业位次',
		href: '../schoolController/zywc'
	}, {
		text: '专业线差',
		href: '../schoolController/zyxc'
	}]
}, {
	text: '分数折合',
	ico: 'fa-edit',
	items: [{
		text: '分数折合',
		href: '../schoolController/fszh'
	}]
}]
new main({
	ico: true
}).config(config);