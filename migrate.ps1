$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$pagesDir = Join-Path $root '_pages'
$outRoot = Join-Path $root 'resources\templates'

# feature grouping (folder per role / bounded context)
$ROLE_MAP = @{
  'admin' = @('dashboard','booking-management','room-management','payment-management','user-management','admin-management','promo-management','discount-management','gallery-management','content-management','reports','analytics','room-form','wifi-management','food-management','check-ins','check-outs','settings')
  'auth'  = @('login','register','forgot-password','reset-password')
  'guest' = @('profile','my-bookings','favorites','search-rooms','food-services','food-cart','wifi','payment-status','money-receipts','notifications','booking-details','booking-confirmation')
  'public'= @('index','rooms','room-details','about','contact','offers','booking','403','404')
}

function RoleOf([string]$name){
  foreach($r in $ROLE_MAP.Keys){ if($ROLE_MAP[$r] -contains $name){ return $r } }
  return $null
}

function Assemble-Page([string]$raw,[string]$name,[string]$subRole){
  $title    = [regex]::Match($raw,"(?s)<!--PAGE-TITLE-->(.*?)<!--PAGE-CSS-->" ).Groups[1].Value.Trim()
  $pageCss  = [regex]::Match($raw,"(?s)<!--PAGE-CSS-->(.*?)<!--PAGE-BODY-->" ).Groups[1].Value.Trim()
  $body     = [regex]::Match($raw,"(?s)<!--PAGE-BODY-->(.*?)<!--PAGE-JS-->" ).Groups[1].Value
  $body     = $body.TrimEnd()
  if($body -match '</body>\s*$'){ $body = $body.Substring(0,$body.LastIndexOf('</body>')) }
  $pageJs   = [regex]::Match($raw,"(?s)<!--PAGE-JS-->(.*?)$",[System.Text.RegularExpressions.RegexOptions]::Singleline).Groups[1].Value.TrimEnd()
  if(-not $title){ $title = $name }

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
<link rel="stylesheet" href="../../static/css/base.css">
<style>
$pageCss
</style>
</head>
$body
<script src="../../static/js/core.js"></script>
<script>
$pageJs
</script>
</body>
</html>
"@
  return $html
}

$files = Get-ChildItem -LiteralPath $pagesDir -Filter *.page
$count = 0; $missing = @()
foreach($f in $files){
  $raw  = Get-Content -LiteralPath $f.FullName -Raw -Encoding UTF8
  $name = [System.IO.Path]::GetFileNameWithoutExtension($f.Name)
  $subRole = RoleOf $name
  if(-not $subRole){ $missing += $name; continue }
  $outDir = Join-Path $outRoot $subRole
  New-Item -ItemType Directory -Force -Path $outDir | Out-Null
  $html = Assemble-Page $raw $name $subRole
  Set-Content -LiteralPath (Join-Path $outDir ($name + '.html')) -Value $html -Encoding UTF8
  $count++
}
Write-Output ("Generated {0} templates." -f $count)
if($missing.Count){ Write-Output ("Unmapped (no role): " + ($missing -join ', ')) }
Write-Output ("Role folders: " + (($ROLE_MAP.Keys | Sort-Object) -join ', '))