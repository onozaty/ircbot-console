var loadLogFiles = function(logFiles) {

  $('#logTitle').text('一覧');

  $('#backList').hide();

  $('#logView').hide();
  $('#logList').html('').show();
  for (var i = 0, len = logFiles.length; i < len; i++) {
    $('#logList').append($('<a style="white-space: nowrap;" href="javascript:void(0)"></a>').text(logFiles[i].replace('.log', ''))).append('&nbsp;\n');
  }

  $('#logList').find('a').click(function() {
    var logDate = $(this).text();
    LogViewer.getLog(channel, logDate + '.log', function(log) {
      $('#logTitle').text(logDate);

      $('#logList').hide();
      $('#logView ul').html('');
      for(var i = 0, len = log.length; i < len; i++) {
        $('#logView ul').append($('<li>').text(log[i]));
      }
      $('#logView').show();

      $('#backList').show();
    })
  });
}

$('#backList').click(function() {
  LogViewer.getFiles(channel, loadLogFiles);
});

$('#backList').hide();

// DWRのローディング表示用の設定
dwr.util._disabledZoneUseCount = 0;
DWRUtil.useLoadingMessage("Loading..");

// チャンネル名取得
var channel = decodeURIComponent(location.href.substring(location.href.lastIndexOf('?') + 1));
$('#channelName').text(channel);

LogViewer.getFiles(channel, loadLogFiles);
