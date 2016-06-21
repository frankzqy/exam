/*
 * Traditional Chinese translation
 * By hata1234
 * 09 April 2007
 */

L5.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">�xȡ��...</div>';

if(L5.View){
    L5.View.prototype.emptyText = "";
}

if(L5.grid.GridPanel){
    L5.grid.GridPanel.prototype.ddText = "�x���� {0} ��";
}

if(L5.TabPanelItem){
    L5.TabPanelItem.prototype.closeText = "�P�]�˘˻`";
}

if(L5.form.Field){
    L5.form.Field.prototype.invalidText = "��ֵ�����ϙ�λҎ��";
}

if(L5.LoadMask){
    L5.LoadMask.prototype.msg = "�xȡ��...";
}

Date.monthNames = [
    "һ��",
    "����",
    "����",
    "����",
    "����",
    "����",
    "����",
    "����",
    "����",
    "ʮ��",
    "ʮһ��",
    "ʮ����"
];

Date.dayNames = [
    "��",
    "һ",
    "��",
    "��",
    "��",
    "��",
    "��"
];

if(L5.MessageBox){
    L5.MessageBox.buttonText = {
        ok : "�_��",
        cancel : "ȡ��",
        yes : "��",
        no : "��"
    };
}

if(L5.util.Format){
    L5.util.Format.date = function(v, format){
       if(!v) return "";
       if(!(v instanceof Date)) v = new Date(Date.parse(v));
       return v.dateFormat(format || "Y/m/d");
    };
}

if(L5.DatePicker){
    L5.apply(L5.DatePicker.prototype, {
       todayText         : "����",
       minText           : "���ڱ�횴����С���S����",
       maxText           : "���ڱ��С�������S����",
       disabledDaysText  : "",
       disabledDatesText : "",
       monthNames        : Date.monthNames,
       dayNames          : Date.dayNames,       
       nextText          : "���� (Ctrl+�ҷ����I)",
       prevText          : "�ς��� (Ctrl+�����I)",
       monthYearText     : "�x���·� (Ctrl+��/�·����I�x�����)",
       todayTip          : "{0} (�հ��I)",
       format            : "y/m/d",
       okText            : "ȷ��",
       cancelText        : "ȡ��"
    });
}

if(L5.PagingToolbar){
    L5.apply(L5.PagingToolbar.prototype, {
       beforePageText : "��",
       afterPageText  : "퓣���{0}�",
       firstText      : "��һ�",
       prevText       : "��һ�",
       nextText       : "��һ�",
       lastText       : "�����",
       refreshText    : "��������",
       displayMsg     : "�@ʾ{0} - {1}�,��{2}�",
       emptyMsg       : '�]���κ��Y��'
    });
}

if(L5.form.TextField){
    L5.apply(L5.form.TextField.prototype, {
       minLengthText : "�˙�λ����Ҫݔ�� {0} ����",
       maxLengthText : "�˙�λ���ݔ�� {0} ����",
       blankText     : "�˙�λ�����",
       regexText     : "",
       emptyText     : null
    });
}

if(L5.form.NumberField){
    L5.apply(L5.form.NumberField.prototype, {
       minText : "�˙�λ֮��ֵ��횴�� {0}",
       maxText : "�˙�λ֮��ֵ���С� {0}",
       nanText : "{0} ���ǺϷ��Ĕ���"
    });
}

if(L5.form.DateField){
    L5.apply(L5.form.DateField.prototype, {
       disabledDaysText  : "�o��ʹ��",
       disabledDatesText : "�o��ʹ��",
       minText           : "�˙�λ֮���ڱ���� {0} ֮��",
       maxText           : "�˙�λ֮���ڱ���� {0} ֮ǰ",
       invalidText       : "{0} �������_�����ڸ�ʽ - ������� �� {1} �� �@�ӵĸ�ʽ",
       format            : "Y/m/d"
    });
}

if(L5.form.ComboBox){
    L5.apply(L5.form.ComboBox.prototype, {
       loadingText       : "�xȡ�� ...",
       valueNotFoundText : undefined
    });
}

if(L5.form.VTypes){
    L5.apply(L5.form.VTypes, {
       emailText    : '�˙�λ���ݔ���� "user@domain.com" ֮E-Mail��ʽ',
       urlText      : '�˙�λ���ݔ���� "http:/'+'/www.domain.com" ֮�Wַ��ʽ',
       alphaText    : '�˙�λ�H��ݔ�����Ӣ����ĸ���׾�( _ )��̖',
       alphanumText : '�˙�λ�H��ݔ�����Ӣ����ĸ�����ּ��׾�( _ )��̖'
    });
}

if(L5.grid.GridView){
    L5.apply(L5.grid.GridView.prototype, {
       sortAscText  : "��������",
       sortDescText : "��������",
       lockText     : "�i����λ",
       unlockText   : "���_��λ�i��",
       columnsText  : "��λ"
    });
}

if(L5.grid.PropertyColumnModel){
    L5.apply(L5.grid.PropertyColumnModel.prototype, {
       nameText   : "���Q",
       valueText  : "��ֵ",
       dateFormat : "Y/m/d"
    });
}

if(L5.layout.BorderLayout && L5.layout.BorderLayout.SplitRegion){
    L5.apply(L5.layout.BorderLayout.SplitRegion.prototype, {
       splitTip            : "��ҷ�s�Ŵ�С.",
       collapsibleSplitTip : "��ҷ�s�Ŵ�С. �����p���[��."
    });
}

L5.connectionError="���ӷ�����ʧ��";
L5.turnPage="ҳ��̫���̫С";
L5.turnPageFirst="�Ѿ��ǵ�һҳ";
L5.turnPageLast="�Ѿ������һҳ";
L5.di="��";
L5.hang="��";
L5.lie="��";
L5.valError="У�����";
L5.parseReturnError="��������ֵ�쳣";
L5.loadDateError="�������ݳ���";