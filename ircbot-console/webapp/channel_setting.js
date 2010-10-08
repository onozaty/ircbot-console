// RSS通知
var rssNotifierConfig = [];
var loadRssNotifierConfig = function(_rssNotifierConfig) {
  rssNotifierConfig = _rssNotifierConfig;
  if (rssNotifierConfig == null) {
    rssNotifierConfig = [];
  }

  resetRssNotifierTable();
}

var resetRssNotifierTable = function() {
  // いったんすべて削除
  var table = $('#rssNotifierTable')[0];
  while(table.rows.length > 1) {
    table.deleteRow(1);
  }

  for(var i = 0, len = rssNotifierConfig.length; i < len; i++) {
    addRssNotifierRow(rssNotifierConfig[i]);
  }
}

var addRssNotifierRow = function(rssNotifier) {
  var table = $('#rssNotifierTable')[0];
  var tr = table.insertRow(table.rows.length);

  var index = table.rows.length - 2;

  setRssNotifierRowValue(rssNotifier, tr, index);
}

var setRssNotifierRowValue = function(rssNotifier, tr, index) {
  var td = $(tr)
    .append('<td>').children()
      .append($('<span>')
        .attr('class', 'icons')
        .append(
          $('<a href="javascript:void(0)"><img src="./images/page_edit.gif"></img>変更</a>')
            .click(function() { editRssNotifier(index, this.parentNode.parentNode.parentNode); return false; }))
        .append('&nbsp;')
        .append($('<a href="javascript:void(0)"><img src="./images/page_cross.gif"></img>削除</a>')
          .click(function() { removeRssNotifier(index, this.parentNode.parentNode.parentNode); return false; })))
      .append($('<p>').text(rssNotifier.feedUrl + '  (周期 ' + rssNotifier.cycleMinute + '分)'));

  if (rssNotifier.messageFormatScript != null && rssNotifier.messageFormatScript != '') {
    td.append($('<pre class="code" style="clear: both;">').text(rssNotifier.messageFormatScript.replace(/\x0d\x0a|\x0d|\x0a/g,'\n')));
  }
}

var removeRssNotifier = function(index, elm) {

  rssNotifierConfig.splice(index, 1);

  // RSS通知情報更新
  resetRssNotifierTable();

  IrcBotServer.updateRssNotifierConfig(channel, rssNotifierConfig);
}

var editRssNotifierIndex = null;
var editRssNotifier = function(index, elm) {

  Glayer.showBox($('#rssNotifierSettingBox')[0]);

  var config = rssNotifierConfig[index];

  $('#rssNotifierUrl').val(config.feedUrl);
  $('#rssNotifierCycleSapn').val(config.cycleMinute);
  $('#rssNotifierFormatText').val(config.messageFormatScript);

  editRssNotifierIndex = index;
}

$('#addRssNotifierButton').click(
  function() {
    Glayer.showBox($('#rssNotifierSettingBox')[0]);

    // 初期化
    $('#rssNotifierCycleSapn').val('1');
    $('#rssNotifierUrl').val('');
    $('#rssNotifierFormatText').val('');
  }
);

$('#rssNotifierSaveButton').click(
  function() {
    var config = {
      feedUrl: $('#rssNotifierUrl').val(),
      cycleMinute: parseInt($('#rssNotifierCycleSapn').val()),
      messageFormatScript: $('#rssNotifierFormatText').val()
    };

    if (config.feedUrl == '') {
      alert('RSSフィードが未入力です。');
      return false;
    }

    for(var i = 0, len = rssNotifierConfig.length; i < len; i++) {
      if (editRssNotifierIndex == i) continue;
      if (rssNotifierConfig[i].feedUrl == config.feedUrl) {
        // 既に存在
        alert('既に登録されているRSSフィードです。');
        return false;
      }
    }

    if (isNaN(config.cycleMinute)) {
      alert('周期時間に数字を入力してください。');
      return false;
    }

    Glayer.hideBox($('#rssNotifierSettingBox')[0]);

    if (editRssNotifierIndex == null) {

      // 追加処理
      rssNotifierConfig.push(config);

    } else {

      // 更新処理
      rssNotifierConfig[editRssNotifierIndex] = config;
    }

    // RSS通知情報更新
    resetRssNotifierTable();

    IrcBotServer.updateRssNotifierConfig(channel, rssNotifierConfig);
  }
);

$('#rssNotifierCancelButton').click(
  function() {
    Glayer.hideBox($('#rssNotifierSettingBox')[0]);
  }
);

//////////////////////////////////////////

// 周期スクリプト
var scriptNotifierConfig = [];
var loadScriptNotifierConfig = function(_scriptNotifierConfig) {
  scriptNotifierConfig = _scriptNotifierConfig;
  if (scriptNotifierConfig == null) {
    scriptNotifierConfig = [];
  }

  // 周期スクリプト情報更新
  resetScriptNotifierTable();
}

var resetScriptNotifierTable = function() {
  // いったんすべて削除
  var table = $('#scriptNotifierTable')[0];
  while(table.rows.length > 1) {
    table.deleteRow(1);
  }

  for(var i = 0, len = scriptNotifierConfig.length; i < len; i++) {
    addScriptNotifierRow(scriptNotifierConfig[i]);
  }
}

var addScriptNotifierRow = function(scriptNotifier) {
  var table = $('#scriptNotifierTable')[0];
  var tr = table.insertRow(table.rows.length);

  var index = table.rows.length - 2;

  setScriptNotifierRowValue(scriptNotifier, tr, index);
}

var setScriptNotifierRowValue = function(scriptNotifier, tr, index) {
  var typeString = '';
  if (scriptNotifier.type == 0) {
    typeString = '周期 ' + scriptNotifier.cyclicSpan + '分';
  } else {
    typeString = '毎日 ' + scriptNotifier.dailyHour + '時' + scriptNotifier.dailyMinute + '分';
  }

  $(tr)
    .append('<td>').children()
      .append($('<span>')
        .attr('class', 'icons')
        .append(
          $('<a href="javascript:void(0)"><img src="./images/page_edit.gif"></img>変更</a>')
            .click(function() { editScriptNotifier(index, this.parentNode.parentNode.parentNode); return false; }))
        .append('&nbsp;')
        .append($('<a href="javascript:void(0)"><img src="./images/page_cross.gif"></img>削除</a>')
          .click(function() { removeScriptNotifier(index, this.parentNode.parentNode.parentNode); return false; })))
      .append($('<span>').text(typeString))
      .append($('<pre class="code" style="clear: both;">').text(scriptNotifier.scriptText.replace(/\x0d\x0a|\x0d|\x0a/g,'\n')));
}

var removeScriptNotifier = function(index, elm) {

  scriptNotifierConfig.splice(index, 1);

  // 周期スクリプト情報更新
  resetScriptNotifierTable();

  IrcBotServer.updateScriptNotifier(channel, scriptNotifierConfig);
}

var editScriptNotifierIndex = null;
var editScriptNotifier = function(index, elm) {

  Glayer.showBox($('#scriptNotifierSettingBox')[0]);

  var config = scriptNotifierConfig[index];

  $('#scriptNotifierText').val(config.scriptText);

  if (config.type == 0) {
    $('#scriptNotifierTypeCycle').attr('checked', true);
  } else {
    $('#scriptNotifierTypeDaily').attr('checked', true);
  }

  $('#scriptNotifierCycleSapn').val(config.cyclicSpan);
  $('#scriptNotifierDailyHour').val(config.dailyHour);
  $('#scriptNotifierDailyMinute').val(config.dailyMinute);

  scriptNotifierTypeChange();

  editScriptNotifierIndex = index;
}

$('#addScriptNotifierButton').click(
  function() {
    Glayer.showBox($('#scriptNotifierSettingBox')[0]);

    // 初期化
    $('#scriptNotifierTypeCycle').attr('checked', true);
    $('#scriptNotifierText').val('');
    $('#scriptNotifierCycleSapn').val('');
    $('#scriptNotifierDailyHour').val(0);
    $('#scriptNotifierDailyMinute').val(0);

    scriptNotifierTypeChange();
  }
);

$('#scriptNotifierSaveButton').click(
  function() {
    var config = {};

    if ($('#scriptNotifierTypeCycle').attr('checked')) {
      config.type = 0;
      config.cyclicSpan = parseInt($('#scriptNotifierCycleSapn').val());

      if (isNaN(config.cyclicSpan)) {
        alert('周期時間に数字を入力してください。');
        return false;
      }
      if (config.cyclicSpan <= 0) {
        alert('周期時間は1以上で入力してください。');
        return false;
      }
    } else {
      config.type = 1;
      config.dailyHour = $('#scriptNotifierDailyHour').val();
      config.dailyMinute = $('#scriptNotifierDailyMinute').val();
    }

    Glayer.hideBox($('#scriptNotifierSettingBox')[0]);

    config.scriptText = $('#scriptNotifierText').val();

    if (editScriptNotifierIndex == null) {

      // 追加処理
      scriptNotifierConfig.push(config);

    } else {

      // 更新処理
      scriptNotifierConfig[editScriptNotifierIndex] = config;
    }

    // 周期スクリプト情報更新
    resetScriptNotifierTable();

    IrcBotServer.updateScriptNotifier(channel, scriptNotifierConfig);
  }
);

$('#scriptNotifierTestButton').click(
  function() {
    IrcBotServer.testScriptNotifier(channel, $('#scriptNotifierText').val());
  }
);

$('#scriptNotifierCancelButton').click(
  function() {
    Glayer.hideBox($('#scriptNotifierSettingBox')[0]);
  }
);

var scriptNotifierTypeChange = function() {
  if ($('#scriptNotifierTypeCycle').attr('checked')) {
    $('#scriptNotifierCycleSapn').removeAttr('disabled');
  } else {
    $('#scriptNotifierCycleSapn').attr('disabled', 'disabled');
  }

  if ($('#scriptNotifierTypeDaily').attr('checked')) {
    $('#scriptNotifierDailyHour').removeAttr('disabled');
    $('#scriptNotifierDailyMinute').removeAttr('disabled');
  } else {
    $('#scriptNotifierDailyHour').attr('disabled', 'disabled');
    $('#scriptNotifierDailyMinute').attr('disabled', 'disabled');
  }
}

$('#scriptNotifierTypeCycle')
  .change(scriptNotifierTypeChange)
  .focus(scriptNotifierTypeChange);
$('#scriptNotifierTypeDaily')
  .change(scriptNotifierTypeChange)
  .focus(scriptNotifierTypeChange);

//////////////////////////////////////////

// メッセージ受信スクリプト
var scriptProcessorConfig = [];
var loadScriptProcessorConfig = function(_scriptProcessorConfig) {
  scriptProcessorConfig = _scriptProcessorConfig;
  if (scriptProcessorConfig == null) {
    scriptProcessorConfig = [];
  }

  // メッセージ受信スクリプト情報更新
  resetScriptProcessorTable();
}

var resetScriptProcessorTable = function() {
  // いったんすべて削除
  var table = $('#scriptProcessorTable')[0];
  while(table.rows.length > 1) {
    table.deleteRow(1);
  }

  for(var i = 0, len = scriptProcessorConfig.length; i < len; i++) {
    addScriptProcessorRow(scriptProcessorConfig[i]);
  }
}

var addScriptProcessorRow = function(scriptText) {
  var table = $('#scriptProcessorTable')[0];
  var tr = table.insertRow(table.rows.length);

  var index = table.rows.length - 2;

  setScriptProcessorRowValue(scriptText, tr, index);
}

var setScriptProcessorRowValue = function(scriptText, tr, index) {

  $(tr)
    .append('<td>').children()
      .append($('<span>')
        .attr('class', 'icons')
        .append(
          $('<a href="javascript:void(0)"><img src="./images/page_edit.gif"></img>変更</a>')
            .click(function() { editScriptProcessor(index, this.parentNode.parentNode.parentNode); return false; }))
        .append('&nbsp;')
        .append($('<a href="javascript:void(0)"><img src="./images/page_cross.gif"></img>削除</a>')
          .click(function() { removeScriptProcessor(index, this.parentNode.parentNode.parentNode); return false; })))
      .append($('<pre class="code" style="clear:both;">').text(scriptText.replace(/\x0d\x0a|\x0d|\x0a/g,'\n')));
}

var removeScriptProcessor = function(index, elm) {

  scriptProcessorConfig.splice(index, 1);

  // メッセージ受信スクリプト情報更新
  resetScriptProcessorTable();

  IrcBotServer.updateScriptProcessor(channel, scriptProcessorConfig);
}

var editScriptProcessorIndex = null;
var editScriptProcessor = function(index, elm) {

  Glayer.showBox($('#scriptProcessorSettingBox')[0]);

  var scriptText = scriptProcessorConfig[index];

  $('#scriptProcessorText').val(scriptText);

  editScriptProcessorIndex = index;
}

$('#addScriptProcessorButton').click(
  function() {
    Glayer.showBox($('#scriptProcessorSettingBox')[0]);

    // 初期化
    $('#scriptProcessorText').val('');
  }
);

$('#scriptProcessorTestButton').click(
  function() {
    var message = window.prompt('メッセージを入力してください。');
    if (message != null) {
      IrcBotServer.testScriptProcessor(channel, $('#scriptProcessorText').val(), message);
    }
  }
);

$('#scriptProcessorSaveButton').click(
  function() {
    Glayer.hideBox($('#scriptProcessorSettingBox')[0]);

    var scriptText = $('#scriptProcessorText').val();

    if (editScriptProcessorIndex == null) {

      // 追加処理
      scriptProcessorConfig.push(scriptText);

    } else {

      // 更新処理
      scriptProcessorConfig[editScriptProcessorIndex] = scriptText;
    }

    // メッセージ受信スクリプト情報更新
    resetScriptProcessorTable();

    IrcBotServer.updateScriptProcessor(channel, scriptProcessorConfig);
  }
);

$('#scriptProcessorCancelButton').click(
  function() {
    Glayer.hideBox($('#scriptProcessorSettingBox')[0]);
  }
);


// DWRのローディング表示用の設定
dwr.util._disabledZoneUseCount = 0;
DWRUtil.useLoadingMessage("Loading..");

// チャンネル名取得
var channel = decodeURIComponent(location.href.substring(location.href.lastIndexOf('?') + 1));
$('#channelName').text(channel);

// ロード処理
IrcBotServer.getRssNotifierConfig(channel, loadRssNotifierConfig);
IrcBotServer.getScriptNotifierConfig(channel, loadScriptNotifierConfig);
IrcBotServer.getScriptProcessorConfig(channel, loadScriptProcessorConfig);
