$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$shared = Join-Path $root '_shared'
$pagesDir = Join-Path $root '_pages'
$outDir = $root

$css = Get-Content -LiteralPath (Join-Path $shared 'design.css.txt') -Raw -Encoding UTF8
$js  = Get-Content -LiteralPath (Join-Path $shared 'core.js.txt') -Raw -Encoding UTF8

function Assemble-Page([string]$raw, [string]$name) {
  $title = ''
  $pageCss = ''
  $body = ''
  $pageJs = ''
  $title = [regex]::Match($raw, "(?s)<!--PAGE-TITLE-->(.*?)<!--PAGE-CSS-->" ).Groups[1].Value.Trim()
  $pageCss = [regex]::Match($raw, "(?s)<!--PAGE-CSS-->(.*?)<!--PAGE-BODY-->" ).Groups[1].Value
  $body = [regex]::Match($raw, "(?s)<!--PAGE-BODY-->(.*?)<!--PAGE-JS-->" ).Groups[1].Value
  $body = $body.TrimEnd()
  if ($body -match '</body>\s*$') { $body = $body.Substring(0, $body.LastIndexOf('</body>')) }
  $pageJs = [regex]::Match($raw, "(?s)<!--PAGE-JS-->(.*?)$", [System.Text.RegularExpressions.RegexOptions]::Singleline ).Groups[1].Value
  $pageJs = $pageJs.TrimEnd()
  $title = $title -replace '^\s+|\s+$',''
  if (-not $title) { $title = $name }
  $html = @"
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>$title</title>
<meta name="description" content="Grand Meridian Resort - professional hotel booking management system">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&family=Playfair+Display:wght@500;600;700&display=swap" rel="stylesheet">
<style>
$css
$pageCss
</style>
</head>
$body
<script>
$js
$pageJs
</script>
</body>
</html>
"@
  return $html
}

$files = Get-ChildItem -LiteralPath $pagesDir -Filter *.page
if (-not $files) { Write-Output 'No page files found.'; exit 1 }
$count = 0
foreach ($f in $files) {
  $raw = Get-Content -LiteralPath $f.FullName -Raw -Encoding UTF8
  $name = [System.IO.Path]::GetFileNameWithoutExtension($f.Name)
  $html = Assemble-Page $raw $name
  $out = Join-Path $outDir ($name + '.html')
  Set-Content -LiteralPath $out -Value $html -Encoding UTF8
  $count++
  Write-Output ("Built {0}.html" -f $name)
}
Write-Output ("Done. {0} pages generated." -f $count)