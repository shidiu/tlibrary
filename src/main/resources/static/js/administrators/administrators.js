$(function() {
	load();
	initDataGrid();

});

function load(){
    //$("#addUser").css('display','none');
    var w=document.getElementById("User").style.width;
    var h=document.getElementById("User").style.height;
    var inputh=parseInt(h)-36;
    $("#input_main").css('height',inputh);
}

function add_user(){

    $("#addUser").css('display','block');

}


function initDataGrid(){
	$('#box').datagrid({
		width:'99.5%',
		height:458,
		remoteSort:false,
		iconCls : 'icon-search',
		columns : [[
		   			 {
				    	field : 'ID',
						title : '编号',
						checkbox : true
				    },
					{
						field : 'LSH',
						title : '流水号',
						sortable:true,
						formatter:dwxx_formatter
					},
					{
						field : 'JYXLMC',
						title : '交易序列名称',
						sortable:true,
						formatter:dwxx_formatter
					},
		]],
		rownumbers:true,
		pagination:true,
	});
    find();
}
function find() {
    var opts = $('#box').datagrid("options");
    if(null == opts.url||""==opts.url){
        opts.url = '/admin/find';
    }
    $('#box').datagrid('load', {

    });
}
function dwxx_formatter(value, row){
	if(value == undefined) {
		value = "";
	}
	return "<lable title='"+value+"'>"+value+"</lable>"
}


