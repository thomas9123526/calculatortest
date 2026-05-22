param(
    [Parameter(Mandatory=$true)]
    [string]$Title,

    [Parameter(Mandatory=$true)]
    [string]$Report,

    [Parameter(Mandatory=$true)]
    [string]$Prompt,

    [string[]]$Paths = @('.')
)

function Get-RepoRoot {
    $current = Get-Location
    while ($current.Path -ne $current.Parent.Path) {
        if (Test-Path (Join-Path $current.Path '.git')) {
            return $current.Path
        }
        Set-Location $current.Parent
        $current = Get-Location
    }
    throw "Unable to find git repository root. Run this script from inside the repo."
}

function Sanitize-ShortTitle {
    param([string]$value)
    $sanitized = $value.Trim() -replace '\s+', '_' -replace '[^0-9A-Za-z_-]', ''
    if ($sanitized.Length -gt 50) {
        $sanitized = $sanitized.Substring(0, 50)
    }
    if ([string]::IsNullOrWhiteSpace($sanitized)) {
        return 'story'
    }
    return $sanitized
}

$repoRoot = Get-RepoRoot
$storyDir = Join-Path $repoRoot 'story_claude'
if (-not (Test-Path $storyDir)) {
    New-Item -ItemType Directory -Path $storyDir | Out-Null
}

$shortTitle = Sanitize-ShortTitle -value $Title
$timestamp = Get-Date -Format 'yyMMdd_HHmmss'
$fileName = "${timestamp}_${shortTitle}.md"
$filePath = Join-Path $storyDir $fileName

$mdContent = @"
# $Title

- date: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')
- filename: $fileName

## Report

$Report

## Prompt

$Prompt
"@

Set-Content -Path $filePath -Value $mdContent -Encoding UTF8

Push-Location $repoRoot
try {
    git add -- "$filePath" | Out-Null
    foreach ($path in $Paths) {
        git add -- $path | Out-Null
    }
    git commit -m $Report
    Write-Host "Committed changes and saved story to $filePath"
} finally {
    Pop-Location
}
