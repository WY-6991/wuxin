import defaultSettings from '@/settings'

const title = defaultSettings.title || 'wuxin-admin'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
