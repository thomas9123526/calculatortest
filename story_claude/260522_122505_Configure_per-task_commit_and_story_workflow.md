# Configure per-task commit and story workflow

- date: 2026-05-22 12:25:05
- filename: 260522_122505_Configure_per-task_commit_and_story_workflow.md

## Report

Set up the per-task commit-and-story workflow for this project.

- Added CLAUDE.md, a working agreement: break work into small tasks and commit each one through the story helper with a report of what was done.
- Added .claude/settings.json with a PreToolUse hook (matches the Bash and PowerShell tools) that blocks any direct git commit and redirects to story_claude/commit_with_story.ps1. The hook command was pipe-tested against four cases before saving.
- Reused the existing story_claude/commit_with_story.ps1 helper, which writes a yyMMdd_HHmmss_shorttitle.md story file and creates the commit in one step.

## Prompt

configure claude that it should commit changes with report that what you have done after every small task done.


I want you to configure claude that save our conversation as md file to story_claude when you do git commit for each task. Also append my prompt at the end.
Md filename can be datetime_shorttitle.md.
datetime means current time as yymmdd_hhss. shorttitle means abstraction title for our conversation. shorttitle length should not more than 50 letters.
